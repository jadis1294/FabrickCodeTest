package com.exercise.fabrick.demo.service;

import com.exercise.fabrick.demo.model.response.ListaTransazioniResponse;
import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;
import com.exercise.fabrick.demo.model.response.ContoCorrenteResponse;
import com.exercise.fabrick.demo.model.response.InviaBonificoResponse;

public interface ContoCorrenteService {
	public ContoCorrenteResponse getSaldoContoCorrente(String accountId, String apiKey, String authSchema);
	public InviaBonificoResponse invioBonifico(String accountId,String apiKey, String authSchema, InviaBonificoRequest request);
	public ListaTransazioniResponse getListaTransizioni(String accountId, String fromAccountingDate,
			String toAccountingDate, String apiKey, String authSchema);
}
