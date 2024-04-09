package com.exercise.fabrick.demo.controller;

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

import com.exercise.fabrick.demo.model.request.InviaBonificoRequest;
import com.exercise.fabrick.demo.model.response.ContoCorrenteResponse;
import com.exercise.fabrick.demo.model.response.InviaBonificoResponse;
import com.exercise.fabrick.demo.model.response.ListaTransazioniResponse;
import com.exercise.fabrick.demo.model.response.ResponseResource;
import com.exercise.fabrick.demo.service.ContoCorrenteService;

@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class ContoCorrenteController {
	@Autowired
	private ContoCorrenteService contoCorrenteService;

	@GetMapping("/getSaldoContoCorrente/{accountId}")
	public ResponseEntity<ContoCorrenteResponse> getSaldoContoCorrente(
			@RequestHeader(value = "Api-Key", required = true) String apiKey,
			@RequestHeader(value = "Auth-Schema", required = true) String authSchema,
			@PathVariable(value = "accountId") String accountId) throws Exception {
		try {
			ContoCorrenteResponse response = contoCorrenteService.getSaldoContoCorrente(accountId, apiKey, authSchema);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok()
					.body(new ContoCorrenteResponse(ResponseResource.RETCODE_ERRORE_NOT_FOUND, "da cambiare e.getMsgRetCode()"));
		}

	}

	@PostMapping("/inviaBonifico/{accountId}")
	public ResponseEntity<InviaBonificoResponse> invioBonifico(
			@RequestHeader(value = "Api-Key", required = true) String apiKey,
			@RequestHeader(value = "Auth-Schema", required = true) String authSchema,
			@PathVariable(value = "accountId") String accountId,
			@RequestBody InviaBonificoRequest request) throws Exception {
		try {
			InviaBonificoResponse response = contoCorrenteService.invioBonifico(accountId, apiKey, authSchema, request);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok()
					.body(new InviaBonificoResponse(ResponseResource.RETCODE_ERRORE_NOT_FOUND, "da cambiare e.getMsgRetCode()"));
		}
	}

	@GetMapping("/getListaTransazioni/{accountId}")
	public ResponseEntity<ListaTransazioniResponse> getListaTransazioni(
			@RequestHeader(value = "Api-Key", required = true) String apiKey,
			@RequestHeader(value = "Auth-Schema", required = true) String authSchema,
			@PathVariable(value = "accountId") String accountId,
			@RequestParam(name = "fromAccountingDate") String fromAccountingDate,
			@RequestParam(name = "fromAccountingDate") String toAccountingDate) throws Exception {
		try {
			ListaTransazioniResponse response = contoCorrenteService.getListaTransizioni(accountId, fromAccountingDate,
					toAccountingDate,apiKey,authSchema);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok()
					.body(new ListaTransazioniResponse(ResponseResource.RETCODE_ERRORE_NOT_FOUND, "da cambiare e.getMsgRetCode()"));
		}
	}

}
