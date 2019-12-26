package com.bitcoin.bitcoin.dto;

import java.io.Serializable;

public class UserDto implements Serializable{
	private String token;
	private String username;
	
	public UserDto() {

	}

	public UserDto(String token, String username) {
		this();
		this.token = token;
		this.token = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
