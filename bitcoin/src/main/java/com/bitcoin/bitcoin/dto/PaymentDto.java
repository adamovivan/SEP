package com.bitcoin.bitcoin.dto;

import java.io.Serializable;
import java.util.Date;

import com.bitcoin.bitcoin.model.Currency;

public class PaymentDto implements Serializable {

	private double totalPrice;
	private String username;

	public PaymentDto() {

	}

	public PaymentDto(double totalPrice, String username) {
		super();
		this.totalPrice = totalPrice;
		this.username = username;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

}
