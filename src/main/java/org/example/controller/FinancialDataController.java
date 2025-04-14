package org.example.controller;

import org.example.model.FinancialData;
import org.example.service.FinancialDataService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/financial-data")
public class FinancialDataController {

    private final FinancialDataService service;

    public FinancialDataController(FinancialDataService service) {
        this.service = service;
    }

    @GetMapping("/{symbol}")
    public Mono<FinancialData> getLatestData(@PathVariable String symbol) {
        return service.getLatestDataForSymbol(symbol);
    }

    @GetMapping("/{symbol}/history")
    public Flux<FinancialData> getHistoricalData(@PathVariable String symbol,
                                                 @RequestParam LocalDate startDate,
                                                 @RequestParam LocalDate endDate) {
        return service.getHistoricalData(symbol, startDate, endDate);
    }
}









