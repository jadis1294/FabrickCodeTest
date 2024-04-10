package com.exercise.fabrick.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
	@JsonProperty
	private String accountCode;

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	
}
