package com.exercise.fabrick.demo.model.response;

import java.util.Date;

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
	private String transactionId;
	private String operationId;
	private Date accountingDate;
	private Date valueDate;
	private double amount;
	private String currency;
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
