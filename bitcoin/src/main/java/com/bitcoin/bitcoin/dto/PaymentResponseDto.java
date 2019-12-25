package com.bitcoin.bitcoin.dto;

import java.io.Serializable;

public class PaymentResponseDto implements Serializable{
	private Long id;
	private String status;
	private String price_currency;
	private String price_amount;
	private String pay_amount;
	private String pay_currency;
	private String receive_amount;
	private String receive_currency;
	private String created_at;
	private String expire;
	private String payment_address;
	private String order_id;
	private String payment_url;

	public PaymentResponseDto() {

	}

	public PaymentResponseDto(Long id, String status, String price_currency, String price_amount, String pay_amount,
			String pay_currency, String receive_amount, String receive_currency, String created_at, String expire,
			String payment_address, String order_id, String payment_url) {
		this();
		this.id = id;
		this.status = status;
		this.price_currency = price_currency;
		this.price_amount = price_amount;
		this.pay_amount = pay_amount;
		this.pay_currency = pay_currency;
		this.receive_amount = receive_amount;
		this.receive_currency = receive_currency;
		this.created_at = created_at;
		this.expire = expire;
		this.payment_address = payment_address;
		this.order_id = order_id;
		this.payment_url = payment_url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrice_currency() {
		return price_currency;
	}

	public void setPrice_currency(String price_currency) {
		this.price_currency = price_currency;
	}

	public String getPrice_amount() {
		return price_amount;
	}

	public void setPrice_amount(String price_amount) {
		this.price_amount = price_amount;
	}

	public String getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}

	public String getPay_currency() {
		return pay_currency;
	}

	public void setPay_currency(String pay_currency) {
		this.pay_currency = pay_currency;
	}

	public String getReceive_amount() {
		return receive_amount;
	}

	public void setReceive_amount(String receive_amount) {
		this.receive_amount = receive_amount;
	}

	public String getReceive_currency() {
		return receive_currency;
	}

	public void setReceive_currency(String receive_currency) {
		this.receive_currency = receive_currency;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getPayment_address() {
		return payment_address;
	}

	public void setPayment_address(String payment_address) {
		this.payment_address = payment_address;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPayment_url() {
		return payment_url;
	}

	public void setPayment_url(String payment_url) {
		this.payment_url = payment_url;
	}
	
	

}
