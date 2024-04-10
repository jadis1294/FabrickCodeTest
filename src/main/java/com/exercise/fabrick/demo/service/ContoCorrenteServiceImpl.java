package com.exercise.fabrick.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.exercise.fabrick.demo.exception.FabrickException;
import com.exercise.fabrick.demo.exception.RecordNotFoundException;
import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;
import com.exercise.fabrick.demo.model.response.ContoCorrenteResponse;
import com.exercise.fabrick.demo.model.response.InviaBonificoResponse;
import com.exercise.fabrick.demo.model.response.ListaTransazioniResponse;
import com.exercise.fabrick.demo.model.response.ResponseResource;
import com.exercise.fabrick.demo.model.response.Transazione;
import com.exercise.fabrick.demo.persistence.entity.Transazioni;
import com.exercise.fabrick.demo.persistence.repository.TransazioniRepository;

import reactor.core.publisher.Mono;

@Service
public class ContoCorrenteServiceImpl implements ContoCorrenteService {
	@Autowired
	private final WebClient webClient;

	@Autowired
	TransazioniRepository transazioniRepository;

	@Autowired
	public ContoCorrenteServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	/**
	 * Operazione: Lettura Transazioni
	 * Input:
	 * o {accountId}:Long è un parametro dell’applicazione;
	 * o {fromAccountingDate}=2019-01-01
	 * o {toAccountingDate}=2019-12-01
	 *
	 * Output: Lista delle transazioni
	 */
	@Override
	public ListaTransazioniResponse getListaTransizioni(String accountId, String fromAccountingDate,
			String toAccountingDate, String apiKey, String authSchema)
			throws FabrickException, RecordNotFoundException {
		listaTransazioniPreliminaryCheck(accountId, fromAccountingDate, toAccountingDate);
		ListaTransazioniResponse listaTransazioniResponse = webClient.get()
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
		if (listaTransazioniResponse == null) {
			throw new RecordNotFoundException("errore durante il recupero delle transazioni");
		}
		if (!listaTransazioniResponse.getList().isEmpty()) {
			for (Transazione transazione : listaTransazioniResponse.getList()) {
				Transazioni transazioneDB = new Transazioni(
						transazione.getTransactionId(),
						transazione.getOperationId(),
						transazione.getAccountingDate(),
						transazione.getValueDate(),
						transazione.getAmount(),
						transazione.getCurrency(),
						transazione.getDescription());
				try {
					transazioniRepository.save(transazioneDB);
				} catch (Exception e) {
					throw new FabrickException("errore durante il salvataggio",
							ResponseResource.RETCODE_ERRORE_GENERICO, e);
				}
			}
		}
		listaTransazioniResponse.setRetCode(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO);
		return listaTransazioniResponse;

	}

	/**
	 * Operazione: Lettura saldo
	 * o Input: {accountId}:Long è un parametro dell’applicazione;
	 * o Output: Visualizzare il saldo
	 */
	@Override
	public ContoCorrenteResponse getSaldoContoCorrente(String accountId, String apiKey, String authSchema)
			throws RecordNotFoundException {
		ContoCorrenteResponse contoCorrenteResponse = webClient.get()
				.uri("/api/gbs/banking/v4.0/accounts/{accountId}/balance", accountId)
				.header("Auth-Schema", authSchema)
				.header("Api-Key", apiKey)
				.retrieve()
				.bodyToMono(ContoCorrenteResponse.class)
				.onErrorResume(e -> Mono.empty())
				.block();
		if (contoCorrenteResponse == null) {
			throw new RecordNotFoundException("errore durante il recupero delle transazioni");
		}
		contoCorrenteResponse.setRetCode(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO);
		return contoCorrenteResponse;

	}

	/**
	 * Operazione: Bonifico
	 * API:
	 * Input (usare per valorizzare il json della chiamata):
	 * o {accountId}:Long è un parametro dell’applicazione;
	 * o {creditor.name}:String, è il beneficiario del bonifico;
	 * o {creditor.account.accountCode}:String, iban del beneficiario;
	 * 
	 * o {description}: String, descrizione del bonifico
	 * o {currency}:String
	 * o {amount}:String
	 * o {executionDate}:String YYYY-MM-DD
	 */
	@Override
	public InviaBonificoResponse invioBonifico(String accountId, String apiKey, String authSchema,
			InviaBonificoRequest request) throws FabrickException {
		invioBonificoPreliminaryCheck(request);
		InviaBonificoResponse inviaBonificoResponse = webClient.post()
				.uri("/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers", accountId)
				.header("Auth-Schema", authSchema)
				.header("Api-Key", apiKey)
				.body(Mono.just(request), InviaBonificoRequest.class)
				.retrieve()
				.bodyToMono(InviaBonificoResponse.class)
				.onErrorResume(e -> Mono.empty())
				.block();
		if (inviaBonificoResponse == null) {
			throw new FabrickException("errore durante il recupero delle transazioni", "99");
		}
		inviaBonificoResponse.setRetCode(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO);
		return inviaBonificoResponse;
	}

	private void invioBonificoPreliminaryCheck(InviaBonificoRequest request) throws FabrickException {
		if(request== null 
		|| request.getAmount()== null 
		|| request.getCreditor() == null
		|| request.getCreditor().getName() ==null
		||  request.getCreditor().getAccount() ==null
		||  request.getCurrency()== null
		|| request.getDescription()== null
		|| request.getExecutionDate() == null){
			throw new FabrickException("Dati in input non corretti", ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}}

	private void listaTransazioniPreliminaryCheck(String accountId, String fromAccountingDate,
			String toAccountingDate) throws FabrickException {
		if(fromAccountingDate== null || toAccountingDate== null || accountId == null ){
			throw new FabrickException("Dati in input non corretti", ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
	}
}
