package com.bitcoin.bitcoin.dto;

import java.io.Serializable;
import java.util.Date;

import com.bitcoin.bitcoin.model.Currency;

public class PaymentDto implements Serializable {
	private String sellerId;
	private Date timestamp;
	private double amount;
	private String redirectUrl;
	private String callbackUrl;
	private Currency currency;
	private String username;

	public PaymentDto() {

	}

	public PaymentDto(String sellerId, Date timestamp, double amount, String redirectUrl, String callbackUrl,
			Currency currency, String username) {
		this();
		this.sellerId = sellerId;
		this.timestamp = timestamp;
		this.amount = amount;
		this.redirectUrl = redirectUrl;
		this.callbackUrl = callbackUrl;
		this.currency = currency;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
