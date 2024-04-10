package com.exercise.fabrick.demo.service;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import com.exercise.fabrick.demo.service.caller.ContoCorrenteServiceCaller;

@Service
public class ContoCorrenteServiceImpl implements ContoCorrenteService {
	private static final Logger logger = LoggerFactory.getLogger(ContoCorrenteServiceImpl.class);
	private static final String YYYYMMDD = "YYYY-MM-DD";
	final SimpleDateFormat formatter = new SimpleDateFormat(YYYYMMDD);

	@Autowired
	TransazioniRepository transazioniRepository;
	@Autowired
	ContoCorrenteServiceCaller caller;

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
		ListaTransazioniResponse listaTransazioniResponse = caller.getListaTransizioni(accountId, fromAccountingDate,
				toAccountingDate, apiKey, authSchema);
		if (listaTransazioniResponse == null) {
			logger.error("errore durante il recupero delle transazioni");
			throw new RecordNotFoundException("errore durante il recupero delle transazioni");
		}
		if (listaTransazioniResponse.getList()!=null && !listaTransazioniResponse.getList().isEmpty()) {
			for (Transazione transazione : listaTransazioniResponse.getList()) {
				saveTransazionedB(transazione);
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
		ContoCorrenteResponse contoCorrenteResponse = caller.getSaldo(accountId, apiKey, authSchema);
		if (contoCorrenteResponse == null) {
			logger.error("errore durante il recupero del saldo del conto corrente");
			throw new RecordNotFoundException("errore durante il recupero del saldo del conto corrente");
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
		InviaBonificoResponse inviaBonificoResponse = caller.invioBonifico(accountId,apiKey,authSchema,request);
		if (inviaBonificoResponse == null) {
			logger.error("errore durante il salvataggio del bonifico");
			throw new FabrickException("errore durante il salvataggio del bonifico", ResponseResource.RETCODE_ERRORE_GENERICO);
		}
		inviaBonificoResponse.setRetCode(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO);
		return inviaBonificoResponse;
	}

	private void invioBonificoPreliminaryCheck(InviaBonificoRequest request) throws FabrickException {
		if (request == null
				|| request.getAmount() == null
				|| request.getCreditor() == null
				|| request.getCreditor().getName() == null
				|| request.getCreditor().getAccount() == null
				|| request.getCurrency() == null
				|| request.getDescription() == null
				|| request.getExecutionDate() == null) {
			throw new FabrickException("Dati in input non corretti", ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
		try {
			formatter.parse(request.getExecutionDate());
		} catch (Exception e) {
			throw new FabrickException("Formato data non coerente", ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
	}

	private void listaTransazioniPreliminaryCheck(String accountId, String fromAccountingDate,
			String toAccountingDate) throws FabrickException {
		if (fromAccountingDate == null || toAccountingDate == null || accountId == null) {
			throw new FabrickException("Dati in input non corretti", ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
		try {
			formatter.parse(fromAccountingDate);
			formatter.parse(toAccountingDate);
		} catch (Exception e) {
			throw new FabrickException("Formato date non coerente", ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
	}

	private void saveTransazionedB(Transazione transazione) throws FabrickException {
		
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
			logger.error("errore durante il salvataggio della transazion a db", e);
			throw new FabrickException("errore durante il salvataggio",
					ResponseResource.RETCODE_ERRORE_GENERICO, e);
		}
	}
}
