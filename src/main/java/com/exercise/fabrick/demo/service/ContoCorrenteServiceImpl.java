package com.exercise.fabrick.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;
import com.exercise.fabrick.demo.model.response.ContoCorrenteResponse;
import com.exercise.fabrick.demo.model.response.InviaBonificoResponse;
import com.exercise.fabrick.demo.model.response.ListaTransazioniResponse;

import reactor.core.publisher.Mono;
@Service
public class ContoCorrenteServiceImpl implements ContoCorrenteService{
	@Autowired
	private final WebClient webClient;

	@Autowired
	public ContoCorrenteServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}



	@Override
	public ListaTransazioniResponse getListaTransizioni(String accountId, String fromAccountingDate,
			String toAccountingDate,String apiKey, String authSchema) {
				ListaTransazioniResponse listaTransazioniResponse = webClient.get()
				.uri(uriBuilder -> uriBuilder
				.path("/api/gbs/banking/v4.0/accounts/{accountId}/transactions")
				.queryParam("fromAccountingDate",fromAccountingDate)
				.queryParam("toAccountingDate",toAccountingDate)
				.build(accountId))
				.header("Auth-Schema",authSchema)
				.header("Api-Key",apiKey)
				.retrieve()
				.bodyToMono(ListaTransazioniResponse.class)
				.onErrorResume(e -> Mono.empty())
				.block();
				return listaTransazioniResponse;
		
	}
	@Override
	public ContoCorrenteResponse getSaldoContoCorrente(String accountId, String apiKey, String authSchema) {
		ContoCorrenteResponse contoCorrenteResponse = webClient.get()
		.uri("/api/gbs/banking/v4.0/accounts/{accountId}/balance",accountId)
		.header("Auth-Schema",authSchema)
		.header("Api-Key",apiKey)
		.retrieve()
		.bodyToMono(ContoCorrenteResponse.class)
		.onErrorResume(e -> Mono.empty())
		.block();

		return contoCorrenteResponse;

	}
	@Override
	public InviaBonificoResponse invioBonifico(String accountId, String apiKey, String authSchema,
			InviaBonificoRequest request) {
				InviaBonificoResponse inviaBonificoResponse = webClient.post()
		.uri("/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers",accountId)
		.header("Auth-Schema",authSchema)
		.header("Api-Key",apiKey)
		.body(Mono.just(request), InviaBonificoRequest.class)
		.retrieve()
		.bodyToMono(InviaBonificoResponse.class)
		.onErrorResume(e -> Mono.empty())
		.block();

		return inviaBonificoResponse;
	}


}
