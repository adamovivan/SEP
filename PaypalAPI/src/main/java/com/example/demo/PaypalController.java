package com.example.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.paypal.base.rest.PayPalRESTException;

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
	public Map<String, Object> payment(@ModelAttribute("order") Order order) {
		    Map<String,Object> paymentCredencial = null;
			try {
				/*
				 * Dva moguca slucaja po povratku sa peypala na osnovu dva url koja su prosledjena:
				 * 1. Ako sve se desi problem -> CANCEL se hendluje na frontu ponistavanjem svega vracanjem na korpu ponovnim pokusajem ...
				 * 2. Ako sve prodje okej -> SUCCESS se hendluje na frontu tako sto kada korisnik potvrdi sve , peypal nazad vraca credencijale,
				 *  odnosno String paymentID,String payerID , i token-koji nam nije bitan ... Sa ovim credencijalima potrebno
				 *  je sa fronta po uspesnoj autentifikacijisa peypalom i potvrdi naloga pozvati metodu completePayment da bi se
				 *  izvrsilo placanje...
				 */
				paymentCredencial = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
						order.getIntent(), order.getDescription(), "http://localhost:4200/cancel",
						"http://localhost:4200/success");
				
			} catch (PayPalRESTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return paymentCredencial;
	}
	
	/**
	 * Ovu medotu pozivamo kada se po povratku sa paypala na osnovu zadatih linkova pogodi http://localhost:4200/success ... gore opisano ...
	 * @param credencial
	 * @return
	 */
	@PostMapping(value = "/completePayment")
	public Map<String, Object> completePayment(@ModelAttribute("credencial") Credencial credencial){
	    return service.completePayment(credencial.getPaymentID(),credencial.getPayerID());
	}
	
}
