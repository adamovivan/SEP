package rs.ac.uns.ftn.paypal_api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paypal.api.payments.*;
import org.springframework.stereotype.Service;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import rs.ac.uns.ftn.paypal_api.model.Order;
import com.paypal.base.rest.OAuthTokenCredential;


/**
 * @author Dejan
 *
 */
@Service
public class PaypalService {
	
//	@Autowired
//	private APIContext context;
//
//	/*
//	 * 1. Payment Creation;
//	 * 2. Payment Confirmation;
//	 */
//	public Payment createPayment(
//			Double total,
//			String intent,
//			String cancelUrl,
//			String successUrl) throws PayPalRESTException{
//		Amount amount = new Amount();
//		amount.setCurrency("USD");
//		total = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
//		amount.setTotal(String.format("%.2f", total));
//
//		Transaction transaction = new Transaction();
//		transaction.setAmount(amount);
//
//		List<Transaction> transactions = new ArrayList<>();
//		transactions.add(transaction);
//
//		Payer payer = new Payer();
//		payer.setPaymentMethod("paypal");
//
//		Payment payment = new Payment();
//		payment.setIntent(intent);
//		payment.setPayer(payer);
//		payment.setTransactions(transactions);
//		RedirectUrls redirectUrls = new RedirectUrls();
//		redirectUrls.setCancelUrl(cancelUrl);
//		redirectUrls.setReturnUrl(successUrl);
//		payment.setRedirectUrls(redirectUrls);
//
//		return payment.create(context);
//	}

	public Payment createPayment(Order order) throws PayPalRESTException{

		Map<String, Object> response = null;
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
		Payment createdPayment;
//		try {
		String redirectUrl = "";
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", "sandbox");
		OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(order.getClientId(),
				order.getClientSecret(), configMap);

		APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
		context.setConfigurationMap(configMap);

		return payment.create(context);
//			createdPayment = payment.create(context);
//			if(createdPayment!=null){
//				List<Links> links = createdPayment.getLinks();
//				for (Links link:links) {
//					if(link.getRel().equals("approval_url")){
//						redirectUrl = link.getHref();
//						break;
//					}
//				}
//				response = new HashMap<String, Object>();
//				response.put("status", "success");
//				response.put("redirect_url", redirectUrl);
//			}
//		} catch (PayPalRESTException e) {
//			System.out.println("Error happened during payment creation!");
//		}
//		return response;

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
