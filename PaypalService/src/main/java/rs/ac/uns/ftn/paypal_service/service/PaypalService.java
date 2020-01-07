package rs.ac.uns.ftn.paypal_service.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import rs.ac.uns.ftn.paypal_service.exception.BadRequestException;
import rs.ac.uns.ftn.paypal_service.exception.NotFoundException;
import rs.ac.uns.ftn.paypal_service.exception.PayPalException;
import rs.ac.uns.ftn.paypal_service.dto.request.OrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.PaymentCompleteRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
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

	public PaymentOrderResponse createPayment(OrderRequest order) throws PayPalRESTException {

		Payment payment = createPaymentInfo(order);

		APIContext context = createPaymentContext(order.getClientId(), order.getClientSecret());

		if (context == null) {
			throw new PayPalException("Failed trying to create context.");
		}

		Payment createdPayment = payment.create(context);

		if (createdPayment == null) {
			throw new PayPalException("Failed trying to create redirect url for PayPal.");
		}

		PaymentOrderResponse paymentOrderResponse = findResponseUrl(createdPayment);
		
		if(!paymentOrderResponse.getSuccess()) {
			throw new PayPalException("Failed trying to retrive url for redirect to PayPal.");
		}
		
		createTransaction(paymentOrderResponse,order);
		
		return paymentOrderResponse;
	}

	public Map<String, Object> completePayment(PaymentCompleteRequest paymentCompleteRequest) {

		validate(paymentCompleteRequest);
		
		Map<String, Object> response = new HashMap<String, Object>();
		Payment payment = new Payment();
		payment.setId(paymentCompleteRequest.getPaymentID());
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(paymentCompleteRequest.getPayerID());
		
		TransactionData transactionData;
		
		if((transactionData = transactionRepository.findByToken(paymentCompleteRequest.getToken())) == null) {
			throw new NotFoundException("Transaction link to that token not exist.");
		}

		PaypalPayment paymentR;
		if((paymentR = paymentRepository.findByUsername(transactionData.getUsername())) == null ) {
			throw new NotFoundException("The payment information associated with that user does not exist");
		}

		APIContext context = createPaymentContext(paymentR.getPaymentId(), paymentR.getPaymentSecret());

		try {

			Payment createdPayment = payment.execute(context, paymentExecution); // TODO context

			if (createdPayment != null) {
				response.put("status", "success");
				response.put("payment", createdPayment);
			}
			
			completeTransaction(createdPayment, transactionData);
			
			logger.info("The payment to " + transactionData.getUsername()
					+ "'s account has been successfully verified by "
					+ createdPayment.getPayer().getPayerInfo().getShippingAddress().getRecipientName());
		} catch (PayPalRESTException e) {
			logger.error("The payment to " + transactionData.getUsername() + "'s account has not been verified");
		}
		return response;
	}
	
	public Boolean cancelTransaction(String token) {
		TransactionData transactionData;
		if((transactionData = transactionRepository.findByToken(token)) == null) {
			throw new NotFoundException("Transaction link to that token not exist.");
		}
		transactionData.setStatus("Canceled");
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		transactionData.setTime(formatter.format(date));
		System.out.println("USAO SAM MALO DA POGLEDAM SAMOOO :D");
		transactionData = transactionRepository.save(transactionData);
		if(transactionData != null)
			return true;
		else
			return false;
	}
	
	

	private PaymentOrderResponse findResponseUrl(Payment createdPayment) {
		String redirectUrl = "";
		List<Links> links = createdPayment.getLinks();
		for (Links link : links) {
			if (link.getRel().equals("approval_url")) {
				redirectUrl = link.getHref();
				break;
			}
		}
		if (!redirectUrl.equals("")) {
			logger.info("Successfully created redirect url for PayPal.");
			return new PaymentOrderResponse(true, redirectUrl);
		}
		return new PaymentOrderResponse(false, redirectUrl);
	}

	private Payment createPaymentInfo(OrderRequest order) {
		Amount amount = new Amount();
		amount.setCurrency("USD");
		Double total = new BigDecimal(order.getTotalPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.format("%.2f", total));
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setReferenceId(UUID.randomUUID().toString());
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
		return payment;
	}

	private APIContext createPaymentContext(String clientID, String clientSecret) {
		Map<String, String> configMap = new HashMap<>();
		configMap.put("mode", "sandbox");
		OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(clientID, clientSecret, configMap);
		try {
			APIContext context = new APIContext(oAuthTokenCredential.getAccessToken());
			context.setConfigurationMap(configMap);
			logger.info("Successfully created payment context.");
			return context;
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private void validate(PaymentCompleteRequest paymentCompleteRequest) {
		if (paymentCompleteRequest == null) {
			throw new BadRequestException("There is not payment complete info.");
		}
		if(paymentCompleteRequest.getPayerID() == null || paymentCompleteRequest.getPaymentID() == null) {
			throw new BadRequestException("The payment secrets is not found.");
		}

	}
	 
	private void createTransaction(PaymentOrderResponse paymentOrderResponse, OrderRequest orderRequest) {
		String token = paymentOrderResponse.getUrl().split("&")[1].split("=")[1];
		TransactionData transaction = new TransactionData();
		transaction.setToken(token);
		System.out.println(orderRequest.getUsername());
		transaction.setUsername(orderRequest.getUsername());
		transaction.setPrice(orderRequest.getTotalPrice());
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		transaction.setTime(formatter.format(date));
		transaction.setStatus("Created");
	    transactionRepository.save(transaction);
	}
	
	private void completeTransaction(Payment payment, TransactionData transactionData) {
		transactionData.setStatus("Completed");
		transactionData.setBuyer(payment.getPayer().getPayerInfo().getShippingAddress().getRecipientName());
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		transactionData.setTime(formatter.format(date));
		transactionRepository.save(transactionData);
	}
}
