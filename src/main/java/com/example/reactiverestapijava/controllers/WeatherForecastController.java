package com.example.reactiverestapijava.controllers;

import com.example.reactiverestapijava.models.ForecastResponse;
import com.example.reactiverestapijava.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Mono<ForecastResponse> getCurrentDayForecast() {
        return weatherService.getCurrentDayForecast();
    }

}
