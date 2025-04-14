package org.example.repository;

import org.example.model.FinancialData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface FinancialDataRepository extends ReactiveMongoRepository<FinancialData, String> {

    Mono<FinancialData> findLatestBySymbol(String symbol);

    Flux<FinancialData> findBySymbolAndDateBetween(String symbol,
                                                   LocalDate startDate,
                                                   LocalDate endDate);
}











