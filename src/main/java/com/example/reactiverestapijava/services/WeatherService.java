package com.example.reactiverestapijava.services;

import com.example.reactiverestapijava.models.DailyForecast;
import com.example.reactiverestapijava.models.ForecastResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.List;
import java.util.Locale;



@Service
public class WeatherService {

    private final WebClient webClient;

    @Autowired
    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.weather.gov").build();
    }

    public Mono<ForecastResponse> getCurrentDayForecast() {
        return webClient.get()
                .uri("/gridpoints/MLB/33,70/forecast")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::transformToDailyForecast);
    }

    private ForecastResponse transformToDailyForecast(JsonNode jsonNode) {
        JsonNode periods = jsonNode.path("properties").path("periods");
        LocalDate today = LocalDate.now();
        String todayName = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        for (JsonNode period : periods) {
            String name = period.path("name").asText();
            if (name.equalsIgnoreCase(todayName)) {
                double temperature = period.path("temperature").asDouble();
                String shortForecast = period.path("shortForecast").asText();
                DailyForecast dailyForecast = new DailyForecast(
                        todayName,
                        convertFahrenheitToCelsius(temperature),
                        shortForecast
                );
                return new ForecastResponse(List.of(dailyForecast));
            }
        }
        return new ForecastResponse(Collections.emptyList());
    }

    private double convertFahrenheitToCelsius(double temperatureFahrenheit) {
        return (temperatureFahrenheit - 32) * 5 / 9;
    }
}

