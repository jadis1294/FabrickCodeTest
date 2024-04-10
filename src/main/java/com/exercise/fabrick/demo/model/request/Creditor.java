package com.exercise.fabrick.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Creditor {
	@JsonProperty
	private String name;
	@JsonProperty
	private Account account;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
	
}
