package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Credencial;
import com.example.demo.model.Order;
import com.example.demo.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
public class PaypalController {

	@Autowired
	PaypalService service;
	
	/**
	 * Front prvo poziva ovu metodu i dobija nazad redirec url na koji je potrebno da se redirektuje da bi korisnik
	 * potvrdio svoje podatke i potvrdio da zeli da mu se sa racuna skine izvesna suma novca
	 * 
	 * @param order
	 * @return
	 */
	@PostMapping("/createPayment")
	public String payment(@RequestBody Order order) {
		System.out.println("Bla: " + order.getIntent() + order.getPrice());
		try {
			Payment payment = service.createPayment(order.getPrice(),order.getIntent(),
					"http://localhost:4200/cancel",
					"http://localhost:4200/success");
			for(Links link:payment.getLinks()) {
				if(link.getRel().equals("approval_url")) {
					return link.getHref();
				}
			}
			
		} catch (PayPalRESTException e) {
		
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Ovu medotu pozivamo kada se po povratku sa paypala na osnovu zadatih linkova pogodi http://localhost:4200/success ... gore opisano ...
	 * @param credencial
	 * @return
	 */
	@PostMapping(value = "/completePayment")
	public Map<String, Object> completePayment(@RequestBody Credencial credencial){
	    return service.completePayment(credencial.getPaymentID(),credencial.getPayerID());
	}
	
}
