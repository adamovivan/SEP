package rs.ac.uns.ftn.paypal_api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.paypal_api.model.Credencial;
import rs.ac.uns.ftn.paypal_api.model.Order;
import rs.ac.uns.ftn.paypal_api.service.PaypalService;
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
		System.out.println("Bla: " + order.getIntent() + " " + order.getTotalPrice());
		try {
			Payment payment = service.createPayment(order);
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
