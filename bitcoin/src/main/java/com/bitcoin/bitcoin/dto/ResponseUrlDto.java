package com.bitcoin.bitcoin.dto;

import java.io.Serializable;

public class ResponseUrlDto implements Serializable {
	private boolean success;
	private String url;

	public ResponseUrlDto() {

	}

	public ResponseUrlDto(boolean success, String ur) {
		this();
		this.success = success;
		this.url = ur;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
