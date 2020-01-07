package com.bitcoin.bitcoin.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderResponseDto implements Serializable {

	private String message;
	private Boolean success;

	public OrderResponseDto() {

	}

	public OrderResponseDto(String message, boolean success) {
		this();
		this.message = message;
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}