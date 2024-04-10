package com.exercise.fabrick.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InviaBonificoRequest {
	@JsonProperty
	private Creditor creditor;
	@JsonProperty
	private String description;
	@JsonProperty
	private String currency;
	@JsonProperty
	private String amount;
	@JsonProperty
	private String executionDate;
	public Creditor getCreditor() {
		return creditor;
	}
	public void setCreditor(Creditor creditor) {
		this.creditor = creditor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}
	
	
}
