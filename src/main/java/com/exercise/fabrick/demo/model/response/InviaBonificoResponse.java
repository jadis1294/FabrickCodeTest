package com.exercise.fabrick.demo.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InviaBonificoResponse extends ResponseResource{
	@JsonProperty
	private String moneyTransferId;
	@JsonProperty
	private String status;
	@JsonProperty
	private String direction;
	@JsonProperty
	private String code;
	@JsonProperty
	private String description;
	
	public String getMoneyTransferId() {
		return moneyTransferId;
	}
	public void setMoneyTransferId(String moneyTransferId) {
		this.moneyTransferId = moneyTransferId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public InviaBonificoResponse(String retCode, String msgRetCode) {
		super(retCode, msgRetCode);
	}

	public InviaBonificoResponse(String retCode, Throwable throwable) {
		super(retCode, throwable);
	}

	public InviaBonificoResponse(String retCode) {
		super(retCode);
	}
}
