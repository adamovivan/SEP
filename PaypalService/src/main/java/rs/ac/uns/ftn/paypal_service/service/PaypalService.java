package rs.ac.uns.ftn.paypal_service.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import rs.ac.uns.ftn.paypal_service.model.Order;

/**
 * @author Dejan
 *
 */
@Service
public class PaypalService {

	public String createPayment(Order order) throws PayPalRESTException{

		Amount amount = new Amount();
		amount.setCurrency("USD");
		
		Double total = new BigDecimal(order.getTotalPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);

		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		//Kreiranje placanja
		Payment payment = new Payment();
		payment.setIntent(order.getIntent());
		payment.setPayer(payer);
		payment.setTransactions(transactions);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(order.getCancelUrl());
		redirectUrls.setReturnUrl(order.getSuccessUrl());
		payment.setRedirectUrls(redirectUrls);
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", "sandbox");
		OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(order.getClientId(),
				order.getClientSecret(), configMap);

		APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
		context.setConfigurationMap(configMap);

		Payment createdPayment = payment.create(context);
		
		for(Links link:createdPayment.getLinks()) {
			if(link.getRel().equals("approval_url")) {
				return link.getHref();
			}
		}
		
		return "false";
	}
	
	
	public Map<String, Object> completePayment(String paymentID,String payerID){
//	    Map<String, Object> response = new HashMap<String, Object>();
//	    Payment payment = new Payment();
//	    payment.setId(paymentID);
//
//	    PaymentExecution paymentExecution = new PaymentExecution();
//	    paymentExecution.setPayerId(payerID);
//	    try {
//	        Payment createdPayment = payment.execute(context, paymentExecution); // TODO context
//	        if(createdPayment!=null){
//	            response.put("status", "success");
//	            response.put("payment", createdPayment);
//	        }
//	    } catch (PayPalRESTException e) {
//	        System.err.println(e.getDetails());
//	    }
//	    return response;
		return null;
	}
	
	
}
