package com.exercise.fabrick.demo.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {
 *  "list": [
 *    {
 *     "transactionId": "1331714087",
 *     "operationId": "00000000273015",
 *     "accountingDate": "2019-04-01",
 *     "valueDate": "2019-04-01",
 *     "type": {
 *       "enumeration": "GBS_TRANSACTION_TYPE",
 *       "value": "GBS_TRANSACTION_TYPE_0023"
 *     },
 *     "amount": -800,
 *     "currency": "EUR",
 *     "description": "BA JOHN DOE PAYMENT INVOICE 75/2017"
 *   },
 *   {
 *     "transactionId": "1331714088",
 *     "operationId": "00000000273015",
 *     "accountingDate": "2019-04-01",
 *     "valueDate": "2019-04-01",
 *     "type": {
 *       "enumeration": "GBS_TRANSACTION_TYPE",
 *       "value": "GBS_TRANSACTION_TYPE_0015"
 *     },
 *     "amount": -1,
 *     "currency": "EUR",
 *     "description": "CO MONEY TRANSFER FEES"
 *   }
 * ]
 *}
 * @author ladt
 *
 */
public class ListaTransazioniResponse extends ResponseResource{
	@JsonProperty
	public List<Transazione> getList() {
		return list;
	}

	public void setList(List<Transazione> list) {
		this.list = list;
	}

	private List<Transazione> list;
	
	public ListaTransazioniResponse(String retCode, String msgRetCode) {
		super(retCode, msgRetCode);
	}

	public ListaTransazioniResponse(String retCode, Throwable throwable) {
		super(retCode, throwable);
	}

	public ListaTransazioniResponse(String retCode) {
		super(retCode);
	}
}
