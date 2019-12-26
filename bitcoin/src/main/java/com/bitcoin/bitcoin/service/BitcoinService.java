package com.bitcoin.bitcoin.service;

import com.bitcoin.bitcoin.dto.PaymentDto;
import com.bitcoin.bitcoin.dto.PaymentResponseDto;
import com.bitcoin.bitcoin.dto.ResponseUrlDto;
import com.bitcoin.bitcoin.model.Merchant;

public interface BitcoinService {

	ResponseUrlDto pay(PaymentDto pdt, String username);
	
	void saveMerchant(Merchant m);
	
	String completePayment(String token);
	String cancelPayment(String token);
}
