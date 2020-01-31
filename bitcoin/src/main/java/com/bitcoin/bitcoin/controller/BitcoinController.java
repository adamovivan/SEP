package com.bitcoin.bitcoin.controller;

import com.bitcoin.bitcoin.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bitcoin.bitcoin.model.Currency;
import com.bitcoin.bitcoin.model.Merchant;
import com.bitcoin.bitcoin.service.BitcoinService;

@RestController
public class BitcoinController {

	
	@Autowired
	private BitcoinService bitcoinService;
	
	//pay
	@PostMapping(path="/pay", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseUrlDto> pay(@RequestBody PaymentDto pdto) {
		ResponseUrlDto returnUrl = this.bitcoinService.pay(pdto, pdto.getUsername());
		System.out.println(returnUrl);
		return new ResponseEntity(returnUrl, HttpStatus.OK);
		
	}
	
	//success 
	@GetMapping(path="success/{token}", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity success(@PathVariable("token") String token) {
		System.out.println(">> success");
		String response = this.bitcoinService.completePayment(token);
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	//metoda koja govori na kom portu radi front od paypala
	@GetMapping(value = "/frontendPort")
	public Long frontendPort(){
		long port = 4202;
	    return port;
	}
	
	//cancel
	@GetMapping(path="cancel/{token}", produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity cancel(@PathVariable("token") String token) {
		String response = this.bitcoinService.cancelPayment(token);
		return new ResponseEntity(response, HttpStatus.OK);
	}
 	
	//save new merchant
	@PostMapping(path="/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity save(@RequestBody UserDto userDto) {
		Merchant m = new Merchant(userDto.getUsername(), userDto.getToken());
		this.bitcoinService.saveMerchant(m);
		return new ResponseEntity<>("Successful added details about bitcoin wallet!", HttpStatus.OK);
	}

	@RequestMapping(value = "/unfinished-transactions-check", method = RequestMethod.GET)
	public ResponseEntity<SimpleResponseDTO> unfinishedTransactionsCheck(){
		bitcoinService.unfinishedTransactionsCheck();
		return ResponseEntity.ok().body(new SimpleResponseDTO("Checked"));
	}
}
