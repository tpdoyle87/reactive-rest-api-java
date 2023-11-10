package com.example.reactiverestapijava.service;

import com.example.reactiverestapijava.exception.WeatherServiceException;
import com.example.reactiverestapijava.model.DailyForecast;
import com.example.reactiverestapijava.model.ForecastResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;



@Service
public class WeatherService {

    private final WebClient webClient;

    @Autowired
    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.weather.gov").build();
    }

    public Mono<ForecastResponse> getCurrentDayForecast(String latitude, String longitude, String office) {
        return webClient.get()
                .uri(String.format("/gridpoints/%s/%s,%s/forecast", office, latitude, longitude))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new WeatherServiceException(
                                "Internal Server Error when calling the weather API.",
                                clientResponse.statusCode().value()
                        ))
                )
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new WeatherServiceException(
                                "Resource not found",
                                clientResponse.statusCode().value()
                        ))
                )
                .bodyToMono(JsonNode.class)
                .map(this::transformToDailyForecast);
    }

    private ForecastResponse transformToDailyForecast(JsonNode jsonNode) {
        JsonNode periods = jsonNode.path("properties").path("periods");
        String currentForecastName = periods.get(0).path("name").asText();
//        List<DailyForecast> dailyForecasts = new ArrayList<>();

        for (JsonNode period : periods) {
            String name = period.path("name").asText();
            if (name.equalsIgnoreCase(currentForecastName)) {
                double temperature = period.path("temperature").asDouble();
                String shortForecast = period.path("shortForecast").asText();
                DailyForecast dailyForecast = new DailyForecast(
                        name,
                        convertFahrenheitToCelsius(temperature),
                        shortForecast
                );
//                dailyForecasts.add(dailyForecast);
                return new ForecastResponse(List.of(dailyForecast));
            }
        }
        return new ForecastResponse(Collections.emptyList());
//        return new ForecastResponse(dailyForecasts);
    }

    private BigDecimal convertFahrenheitToCelsius(double temperatureFahrenheit) {
        BigDecimal bd = BigDecimal.valueOf((temperatureFahrenheit - 32) * 5 / 9);
        return  bd.setScale(1, RoundingMode.HALF_UP );
    }
}

