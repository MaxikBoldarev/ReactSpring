package org.example.service;

public class ArimaResponse {
    private String prediction;
    private Double forecastValue;

    // Геттеры и сеттеры
    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public Double getForecastValue() {
        return forecastValue;
    }

    public void setForecastValue(Double forecastValue) {
        this.forecastValue = forecastValue;
    }
}
