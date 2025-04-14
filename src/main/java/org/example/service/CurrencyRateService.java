package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.FinancialData;
import org.example.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final CurrencyService currencyService;
    private final CurrencyRateParserXml currencyRateParser;
    private final String url = "https://cbr.ru/scripts/XML_daily.asp";

    private final CurrencyRepository currencyRepository;

    public FinancialData getCurrencyRate(String currency, LocalDate date) {
        List<FinancialData> rates;

        var urlWithParams = String.format("%s?date_req=%s", url, DATE_FORMATTER.format(date));
        var ratesAsXml = currencyService.getRatesAsXml(urlWithParams);
        rates = currencyRateParser.parse(ratesAsXml);
        currencyRepository.deleteAll();
        currencyRepository.saveAll(rates);

        return rates.stream().filter(rate -> currency.equals(rate.getCharCode()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Currency Rate not found. Currency:" + currency + ", date:" + date));
    }
}