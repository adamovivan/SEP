package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;



/**
 * @author Dejan
 *
 */
@Service
public class PaypalService {
	
	String clientId = "AWure9h6GXJwJ3YEfVd5YPIpnqrldESUsElWx9B5Kr8Ykc4_cvz9QM5KaTv4Jh743W3B_PMEBtjFfuJd";
	String clientSecret = "EBOgOZbYvvThwaeKGiPgTTjYs4NIxGuNZEcE6SPnshx_K-GgspvKhgQzBfbguYJ2ioq1kGMVfu6k7bc0";
	
	/*
	 * 1. Payment Creation;
	 * 2. Payment Confirmation;
	 */
	public Map<String, Object> createPayment(
			Double total, 
			String currency, 
			String method,
			String intent,
			String description, 
			String cancelUrl, 
			String successUrl) throws PayPalRESTException{
		
		Map<String, Object> response = null;
		//postavljanje ukupne cene za placanje
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(Double.toString(total));

		//opis transakcije i suma
		Transaction transaction = new Transaction();
		transaction.setDescription(description);
		transaction.setAmount(amount);

		
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		//postavljanje nacina placanja
		Payer payer = new Payer();
		payer.setPaymentMethod(method.toString());

		//Kreiranje placanja
		Payment payment = new Payment();
		payment.setIntent(intent.toString());
		payment.setPayer(payer);  
		payment.setTransactions(transactions);
		
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		Payment createdPayment;
		try {
	        String redirectUrl = "";
	        Map<String, String> configMap = new HashMap<>();
			configMap.put("mode", "sandbox");
	        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientId, 
	        																	clientSecret, configMap);
	        
	        APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
	        context.setConfigurationMap(configMap);
	        
	        createdPayment = payment.create(context);
	        if(createdPayment!=null){
	            List<Links> links = createdPayment.getLinks();
	            for (Links link:links) {
	                if(link.getRel().equals("approval_url")){
	                    redirectUrl = link.getHref();
	                    break;
	                }
	            }
	            response = new HashMap<String, Object>();
	            response.put("status", "success");
	            response.put("redirect_url", redirectUrl);
	        }
	    } catch (PayPalRESTException e) {
	        System.out.println("Error happened during payment creation!");
	    }
	    return response;
	    
	}
	
	public Map<String, Object> completePayment(String paymentID,String payerID){
	    Map<String, Object> response = new HashMap<String, Object>();
	    Payment payment = new Payment();
	    payment.setId(paymentID);

	    PaymentExecution paymentExecution = new PaymentExecution();
	    paymentExecution.setPayerId(payerID);
	    try {
	    	 Map<String, String> configMap = new HashMap<>();
			configMap.put("mode", "sandbox");
	        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientId, 
	        																	clientSecret, configMap);
	        
	        APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
	        Payment createdPayment = payment.execute(context, paymentExecution);
	        if(createdPayment!=null){
	            response.put("status", "success");
	            response.put("payment", createdPayment);
	        }
	    } catch (PayPalRESTException e) {
	        System.err.println(e.getDetails());
	    }
	    return response;
	}
	
	
}
