package com.exercise.fabrick.demo.service;

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
import com.exercise.fabrick.demo.utils.ContoCorrenteUtils;

@Service
public class ContoCorrenteServiceImpl implements ContoCorrenteService {
	private static final Logger logger = LoggerFactory.getLogger(ContoCorrenteServiceImpl.class);
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
	public ListaTransazioniResponse getListaTransizioni(final String accountId, final String fromAccountingDate,
			final String toAccountingDate, final String apiKey, final String authSchema)
			throws FabrickException, RecordNotFoundException {
		listaTransazioniPreliminaryCheck(accountId, fromAccountingDate, toAccountingDate);
		final ListaTransazioniResponse listaTransazioniResponse = caller.getListaTransizioni(accountId, fromAccountingDate,
				toAccountingDate, apiKey, authSchema);
		if (listaTransazioniResponse == null) {
			logger.error(ContoCorrenteUtils.ERRORE_RECUPERO_TRANSAZIONI);
			throw new RecordNotFoundException(ContoCorrenteUtils.ERRORE_RECUPERO_TRANSAZIONI);
		}
		if (listaTransazioniResponse.getList()!=null && !listaTransazioniResponse.getList().isEmpty()) {
			for (final Transazione transazione : listaTransazioniResponse.getList()) {
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
	public ContoCorrenteResponse getSaldoContoCorrente(final String accountId, final String apiKey, final String authSchema)
			throws RecordNotFoundException {
		final ContoCorrenteResponse contoCorrenteResponse = caller.getSaldo(accountId, apiKey, authSchema);
		if (contoCorrenteResponse == null) {
			logger.error(ContoCorrenteUtils.ERRORE_RECUPERO_SALDO);
			throw new RecordNotFoundException(ContoCorrenteUtils.ERRORE_RECUPERO_SALDO);
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
	public InviaBonificoResponse invioBonifico(final String accountId, final String apiKey, final String authSchema,
			final InviaBonificoRequest request) throws FabrickException {
		invioBonificoPreliminaryCheck(request);
		final InviaBonificoResponse inviaBonificoResponse = caller.invioBonifico(accountId,apiKey,authSchema,request);
		if (inviaBonificoResponse == null) {
			logger.error(ContoCorrenteUtils.ERRORE_INVIO_BONIFICO);
			throw new FabrickException(ContoCorrenteUtils.ERRORE_INVIO_BONIFICO, ResponseResource.RETCODE_ERRORE_GENERICO);
		}
		if(inviaBonificoResponse.getStatus().equals("KO")){
			inviaBonificoResponse.setCode("API000");
			inviaBonificoResponse.setDescription("Errore tecnico La condizione BP049 non è prevista per il conto id " + accountId);
			inviaBonificoResponse.setRetCode(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO);
			return inviaBonificoResponse;
		}
		inviaBonificoResponse.setRetCode(ResponseResource.RETCODE_OPERAZIONE_CONCLUSA_CON_SUCCESSO);
		return inviaBonificoResponse;
	}

	private void invioBonificoPreliminaryCheck(final InviaBonificoRequest request) throws FabrickException {
		if (request == null
				|| request.getAmount() == null
				|| request.getCreditor() == null
				|| request.getCreditor().getName() == null
				|| request.getCreditor().getAccount() == null
				|| request.getCurrency() == null
				|| request.getDescription() == null
				|| request.getExecutionDate() == null) {
			throw new FabrickException(ContoCorrenteUtils.ERRORE_DATI_INPUT, ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
		try {
			ContoCorrenteUtils.formatter.parse(request.getExecutionDate());
		} catch (final Exception e) {
			throw new FabrickException(ContoCorrenteUtils.ERRORE_FORMATO_DATA, ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
	}

	private void listaTransazioniPreliminaryCheck(final String accountId, final String fromAccountingDate,
			final String toAccountingDate) throws FabrickException {
		if (fromAccountingDate == null || toAccountingDate == null || accountId == null) {
			throw new FabrickException(ContoCorrenteUtils.ERRORE_DATI_INPUT, ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
		try {
			ContoCorrenteUtils.formatter.parse(fromAccountingDate);
			ContoCorrenteUtils.formatter.parse(toAccountingDate);
		} catch (final Exception e) {
			throw new FabrickException(ContoCorrenteUtils.ERRORE_FORMATO_DATA, ResponseResource.RETCODE_INPUT_NON_COERENTE);
		}
	}

	private void saveTransazionedB(final Transazione transazione) throws FabrickException {
		
		final Transazioni transazioneDB = new Transazioni(
				transazione.getTransactionId(),
				transazione.getOperationId(),
				transazione.getAccountingDate(),
				transazione.getValueDate(),
				transazione.getAmount(),
				transazione.getCurrency(),
				transazione.getDescription());
		try {
			transazioniRepository.save(transazioneDB);
		} catch (final Exception e) {
			logger.error(ContoCorrenteUtils.ERRORE_SALVATAGGIO_DB, e);
			throw new FabrickException(ContoCorrenteUtils.ERRORE_SALVATAGGIO_DB,
					ResponseResource.RETCODE_ERRORE_GENERICO, e);
		}
	}
}
