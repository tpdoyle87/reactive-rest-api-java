package com.example.reactiverestapijava.controller;

import com.example.reactiverestapijava.model.ForecastResponse;
import com.example.reactiverestapijava.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/forecast")
public class WeatherForecastController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherForecastController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public Mono<ForecastResponse> getCurrentDayForecast(
        @RequestParam(value = "latitude", defaultValue = "33") String latitude,
        @RequestParam(value = "longitude", defaultValue = "70") String longitude,
        @RequestParam(value = "office", defaultValue = "MLB") String office
    ) {
        return weatherService.getCurrentDayForecast(latitude, longitude, office);
    }

}
