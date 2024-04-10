package com.exercise.fabrick.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.fabrick.demo.exception.FabrickException;
import com.exercise.fabrick.demo.exception.RecordNotFoundException;
import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;
import com.exercise.fabrick.demo.model.response.ContoCorrenteResponse;
import com.exercise.fabrick.demo.model.response.InviaBonificoResponse;
import com.exercise.fabrick.demo.model.response.ListaTransazioniResponse;
import com.exercise.fabrick.demo.model.response.ResponseResource;
import com.exercise.fabrick.demo.service.ContoCorrenteService;
import com.exercise.fabrick.demo.utils.ContoCorrenteUtils;

@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class ContoCorrenteController {
	private static final Logger logger = LoggerFactory.getLogger(ContoCorrenteController.class);
	@Autowired
	private ContoCorrenteService contoCorrenteService;

	@GetMapping("/getSaldoContoCorrente/{accountId}")
	public ResponseEntity<ContoCorrenteResponse> getSaldoContoCorrente(
			@RequestHeader(value = ContoCorrenteUtils.API_KEY, required = true) String apiKey,
			@RequestHeader(value = ContoCorrenteUtils.AUTH_SCHEMA, required = true) String authSchema,
			@PathVariable(value = "accountId") String accountId) throws Exception {
		try {
			logger.info("[ContoCorrenteController][getSaldoContoCorrente] - called");
			ContoCorrenteResponse response = contoCorrenteService.getSaldoContoCorrente(accountId, apiKey, authSchema);
			return ResponseEntity.ok().body(response);
		} catch (RecordNotFoundException e) {
			return ResponseEntity.ok()
					.body(new ContoCorrenteResponse(ResponseResource.RETCODE_ERRORE_NOT_FOUND, e.getMsgRetCode()));
		}

	}

	@PostMapping("/inviaBonifico/{accountId}")
	public ResponseEntity<InviaBonificoResponse> invioBonifico(
			@RequestHeader(value = ContoCorrenteUtils.API_KEY, required = true) String apiKey,
			@RequestHeader(value = ContoCorrenteUtils.AUTH_SCHEMA, required = true) String authSchema,
			@PathVariable(value = "accountId") String accountId,
			@RequestBody InviaBonificoRequest request) throws Exception {
		try {
			logger.info("[ContoCorrenteController][invioBonifico] - called");
			InviaBonificoResponse response = contoCorrenteService.invioBonifico(accountId, apiKey, authSchema, request);
			return ResponseEntity.ok().body(response);
		} catch (FabrickException e) {
			return ResponseEntity.ok()
					.body(new InviaBonificoResponse(ResponseResource.RETCODE_ERRORE_GENERICO,  e.getMsgRetCode()));
		}
	}

	@GetMapping("/getListaTransazioni/{accountId}")
	public ResponseEntity<ListaTransazioniResponse> getListaTransazioni(
			@RequestHeader(value = ContoCorrenteUtils.API_KEY, required = true) String apiKey,
			@RequestHeader(value = ContoCorrenteUtils.AUTH_SCHEMA, required = true) String authSchema,
			@PathVariable(value = "accountId") String accountId,
			@RequestParam(name = "fromAccountingDate") String fromAccountingDate,
			@RequestParam(name = "fromAccountingDate") String toAccountingDate) throws Exception {
		try {
			logger.info("[ContoCorrenteController][getListaTransazioni] - called");
			ListaTransazioniResponse response = contoCorrenteService.getListaTransizioni(accountId, fromAccountingDate,
					toAccountingDate,apiKey,authSchema);
			return ResponseEntity.ok().body(response);
		} catch (RecordNotFoundException e) {
			return ResponseEntity.ok()
					.body(new ListaTransazioniResponse(ResponseResource.RETCODE_ERRORE_NOT_FOUND, e.getMsgRetCode()));
		}
		catch (FabrickException e) {
			return ResponseEntity.ok()
					.body(new ListaTransazioniResponse(ResponseResource.RETCODE_ERRORE_GENERICO,  e.getMsgRetCode()));
		}
	}

}
