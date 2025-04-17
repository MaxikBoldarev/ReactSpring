package org.example.service;

import java.time.LocalDate;

public class CurrencyRequest {
    private String currency;
    private LocalDate date;
    private Double rate;

    // Геттеры и сеттеры
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
