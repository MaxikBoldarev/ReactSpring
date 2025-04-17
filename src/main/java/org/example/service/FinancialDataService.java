package org.example.service;

import org.example.model.FinancialData;
import org.example.repository.FinancialDataRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
public class FinancialDataService {

    private final FinancialDataRepository repository;
    private final ExternalApiClient apiClient;

    public FinancialDataService(FinancialDataRepository repository, ExternalApiClient apiClient) {
        this.repository = repository;
        this.apiClient = apiClient;
    }

    public Mono<FinancialData> getLatestDataForSymbol(String symbol) {
        return repository.findLatestBySymbol(symbol)
                .switchIfEmpty(apiClient.fetchLatestData(symbol)
                        .flatMap(repository::save));
    }

    public Flux<FinancialData> getHistoricalData(String symbol,
                                                 LocalDate startDate,
                                                 LocalDate endDate) {
        return repository.findBySymbolAndDateBetween(symbol, startDate, endDate)
                .switchIfEmpty(apiClient.fetchHistoricalData(symbol, startDate, endDate)
                        .flatMap(repository::saveAll)).subscribe(CurrencyServiceArima.routes());
    }

    public String getRatesAsXml(String url) {
        try {
            var client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            throw new RuntimeException(ex);
        }
    }
}





