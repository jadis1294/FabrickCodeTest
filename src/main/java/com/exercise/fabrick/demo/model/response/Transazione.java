package com.exercise.fabrick.demo.model.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
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
 *   }
 * @author ladt
 *
 */
public class Transazione {
	@JsonProperty
	private String transactionId;
	@JsonProperty
	private String operationId;
	@JsonProperty
	private Date accountingDate;
	@JsonProperty
	private Date valueDate;
	@JsonProperty
	private double amount;
	@JsonProperty
	private String currency;
	@JsonProperty
	private String description;
	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public Date getAccountingDate() {
		return accountingDate;
	}
	public void setAccountingDate(Date accountingDate) {
		this.accountingDate = accountingDate;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
