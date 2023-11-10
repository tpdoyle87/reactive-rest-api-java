package com.example.reactiverestapijava.model;

import java.math.BigDecimal;

public class DailyForecast {
    private String day_name;
    private BigDecimal temp_high_celsius;
    private String forecast_blurp;

    public DailyForecast(String day_name, BigDecimal temp_high_celsius, String forecast_blurp) {
        this.day_name = day_name;
        this.temp_high_celsius = temp_high_celsius;
        this.forecast_blurp = forecast_blurp;
    }


    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public BigDecimal getTemp_high_celsius() {
        return temp_high_celsius;
    }

    public void setTemp_high_celsius(BigDecimal temp_high_celsius) {
        this.temp_high_celsius = temp_high_celsius;
    }

    public String getForecast_blurp() {
        return forecast_blurp;
    }

    public void setForecast_blurp(String forecast_blurp) {
        this.forecast_blurp = forecast_blurp;
    }
}
