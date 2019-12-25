package com.bitcoin.bitcoin.dto;

import java.util.Date;

import com.bitcoin.bitcoin.model.Currency;
import com.bitcoin.bitcoin.model.PaymentState;

public class OrderDto {
	private String paymentId;
	private Date createDate;
	private Date updateDate;
	private double amount;
	private Currency currency;
	private PaymentState state;

	public OrderDto() {

	}

	public OrderDto(String paymentId, Date createDate, Date updateDate, double amount, Currency currency,
			PaymentState state) {
		this();
		this.paymentId = paymentId;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.amount = amount;
		this.currency = currency;
		this.state = state;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public PaymentState getState() {
		return state;
	}

	public void setState(PaymentState state) {
		this.state = state;
	}

}
