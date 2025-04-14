package org.example.service;

import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class ExternalApiClient {
    public Flux<Object> fetchLatestData(String symbol) {
        return Flux.switchOnNext(Subscriber::onComplete);
    }
}
