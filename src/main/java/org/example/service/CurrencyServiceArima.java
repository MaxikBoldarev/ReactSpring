package org.example.service;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class CurrencyServiceArima {
    @Bean
    public WebClient webClient() {
        return WebClient.create("http://arima-service");
    }

    @Bean
    public RouterFunction<ServerResponse> routes(WebClient webClient) {
        return RouterFunctions.route()
                .POST("/currency", request -> handleCurrencyRequest(request, webClient))
                .build();
    }

    private Mono<ServerResponse> handleCurrencyRequest(ServerRequest request, WebClient webClient) {
        return request.bodyToMono(CurrencyRequest.class)
                .flatMap(currencyRequest -> {
                    return webClient.post()
                            .uri("/j-arima")
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(currencyRequest)
                            .retrieve()
                            .bodyToMono(ArimaResponse.class)
                            .flatMap(arimaResponse ->
                                    ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(arimaResponse)
                            )
                            .onErrorResume(e -> {
                                System.err.println("Ошибка при обращении к микросервису ARIMA: " + e.getMessage());
                                return ServerResponse.status(500)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(new ErrorResponse() {
                                            @Override
                                            public HttpStatusCode getStatusCode() {
                                                return null;
                                            }

                                            @Override
                                            public ProblemDetail getBody() {
                                                return null;
                                            }
                                        });
                            });
                });
    }
}
