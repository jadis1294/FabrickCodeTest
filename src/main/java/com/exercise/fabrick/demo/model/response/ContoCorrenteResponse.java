package com.exercise.fabrick.demo.model.response;

import java.util.Date;

/**
 * {
 * 	"date": "2018-08-17",
 * 	"balance": 29.64,
 * 	"availableBalance": 29.64,
 * 	"currency": "EUR"
 * }
 * @author ladt
 *
 */
public class ContoCorrenteResponse extends ResponseResource{
	private Date date;
	private double balance;
	private double availableBalance;
	private String currency;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public ContoCorrenteResponse(String retCode, String msgRetCode) {
		super(retCode, msgRetCode);
	}

	public ContoCorrenteResponse(String retCode, Throwable throwable) {
		super(retCode, throwable);
	}

	public ContoCorrenteResponse(String retCode) {
		super(retCode);
	}

}
