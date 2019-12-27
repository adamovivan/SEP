package rs.ac.uns.ftn.paypal_service.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import rs.ac.uns.ftn.paypal_service.dto.request.PaymentCompleteRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.model.Order;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.model.TransactionData;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;
import rs.ac.uns.ftn.paypal_service.repository.TransactionRepository;

/**
 * @author Dejan
 *
 */
@Service
public class PaypalService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaypalService.class);
	
	@Autowired
    private PaymentRepository paymentRepository;
	
	@Autowired
    private TransactionRepository transactionRepository;

	public PaymentOrderResponse createPayment(Order order) throws PayPalRESTException{
		//potrebne info vezane za paypall
		String redirectUrl = "";
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
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(order.getCancelUrl());
		redirectUrls.setReturnUrl(order.getSuccessUrl());
		payment.setRedirectUrls(redirectUrls);
		
		//sandbox oznacava da ce se raditi sa fake parama...
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", "sandbox");
		
		try {
			
			OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(order.getClientId(),
					order.getClientSecret(), configMap);
			APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
			context.setConfigurationMap(configMap);
			Payment createdPayment = payment.create(context);
			if(createdPayment != null) {
				List<Links> links = createdPayment.getLinks();
				for(Links link : links) {
					if(link.getRel().equals("approval_url")) {
						redirectUrl = link.getHref();
						break;
					}
				}
				if(!redirectUrl.equals("")) {
					return new PaymentOrderResponse(true, redirectUrl);
				}
			}
		} catch (Exception e) {
			System.out.println("Error happened during payment creation!");
		}
		return new PaymentOrderResponse(false, redirectUrl);
	}
	
	
	public Map<String, Object> completePayment(PaymentCompleteRequest paymentCompleteRequest){
	    Map<String, Object> response = new HashMap<String, Object>();
	    Payment payment = new Payment();
	    payment.setId(paymentCompleteRequest.getPaymentID());

	    PaymentExecution paymentExecution = new PaymentExecution();
	    paymentExecution.setPayerId(paymentCompleteRequest.getPayerID());
	    
	    Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", "sandbox");
	    try {
	    	PaypalPayment paymentR = paymentRepository.findByUsername(paymentCompleteRequest.getUsername());
	    	OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(paymentR.getPaymentId(),
	    			paymentR.getPaymentSecret(), configMap);
			APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
			context.setConfigurationMap(configMap);
			
	        Payment createdPayment = payment.execute(context, paymentExecution); // TODO context
	        
	        if(createdPayment!=null){
	            response.put("status", "success");
	            response.put("payment", createdPayment);
	            TransactionData transaction = new TransactionData();
	            transaction = transactionRepository.findByUsername(paymentCompleteRequest.getUsername());
	            transaction.setStatus("completed");
	            transaction.setBuyer(createdPayment.getPayer().getPayerInfo().getShippingAddress().getRecipientName());
	            transaction = transactionRepository.save(transaction);
	        }
	        logger.info("The payment to "+paymentCompleteRequest.getUsername()+"'s account has been successfully verified by " + 
	        		createdPayment.getPayer().getPayerInfo().getShippingAddress().getRecipientName());
	    } catch (PayPalRESTException e) {
	    	logger.error("The payment to "+paymentCompleteRequest.getUsername()+"'s account has not been verified");
	        System.err.println(e.getDetails());
	    }
	    return response;
	}
	
	/*public Boolean cancelPayment(String username) {
		TransactionData transaction = new TransactionData();
        transaction = transactionRepository.findByUsername(username);
        transaction.setStatus("canceled");
        transaction.setBuyer("//");
        transaction = transactionRepository.save(transaction);
        if(transaction != null) {
        	 return true;
        }
        return false;
	}*/
	
	
}
