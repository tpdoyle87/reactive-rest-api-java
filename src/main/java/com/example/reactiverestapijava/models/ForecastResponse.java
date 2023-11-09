package com.example.reactiverestapijava.models;

import java.util.List;

public class ForecastResponse {
    private List<DailyForecast> daily;

    public ForecastResponse(List<DailyForecast> daily) {
        this.daily = daily;
    }

    public List<DailyForecast> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyForecast> daily) {
        this.daily = daily;
    }
}
