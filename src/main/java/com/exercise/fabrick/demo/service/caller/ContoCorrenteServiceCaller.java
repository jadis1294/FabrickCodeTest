package com.exercise.fabrick.demo.service.caller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;
import com.exercise.fabrick.demo.model.response.ContoCorrenteResponse;
import com.exercise.fabrick.demo.model.response.InviaBonificoResponse;
import com.exercise.fabrick.demo.model.response.ListaTransazioniResponse;

import reactor.core.publisher.Mono;

@Service
public class ContoCorrenteServiceCaller {
    @Autowired
    private WebClient webClient;

    public ListaTransazioniResponse getListaTransizioni(String accountId, String fromAccountingDate,
            String toAccountingDate, String apiKey,
            String authSchema) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/gbs/banking/v4.0/accounts/{accountId}/transactions")
                        .queryParam("fromAccountingDate", fromAccountingDate)
                        .queryParam("toAccountingDate", toAccountingDate)
                        .build(accountId))
                .header("Auth-Schema", authSchema)
                .header("Api-Key", apiKey)
                .retrieve()
                .bodyToMono(ListaTransazioniResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
    }

    public ContoCorrenteResponse getSaldo(String accountId, String apiKey, String authSchema) {
        return webClient.get()
                .uri("/api/gbs/banking/v4.0/accounts/{accountId}/balance", accountId)
                .header("Auth-Schema", authSchema)
                .header("Api-Key", apiKey)
                .retrieve()
                .bodyToMono(ContoCorrenteResponse.class)
                .onErrorResume(e -> Mono.empty())
                .block();
    }

    public InviaBonificoResponse invioBonifico(String accountId, String apiKey, String authSchema,
            InviaBonificoRequest request) {
                return webClient.post()
				.uri("/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers", accountId)
				.header("Auth-Schema", authSchema)
				.header("Api-Key", apiKey)
				.body(Mono.just(request), InviaBonificoRequest.class)
				.retrieve()
				.bodyToMono(InviaBonificoResponse.class)
				.onErrorResume(e -> Mono.empty())
				.block();
    }
}