package com.bitcoin.bitcoin.exception;

@SuppressWarnings("serial")
public class BitcoinUserNotExistException extends RuntimeException {
	public BitcoinUserNotExistException() {

	}

	public BitcoinUserNotExistException(String message) {
		super(message);
	}

}
