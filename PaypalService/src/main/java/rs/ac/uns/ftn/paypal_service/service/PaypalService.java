package rs.ac.uns.ftn.paypal_service.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
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
import org.springframework.web.client.RestTemplate;

import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.ChargeModels;
import com.paypal.api.payments.Currency;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.MerchantPreferences;
import com.paypal.api.payments.Patch;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentDefinition;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Plan;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import rs.ac.uns.ftn.paypal_service.config.TransactionStatus;
import rs.ac.uns.ftn.paypal_service.dto.request.AgreementCompleteRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.AgreementRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.NotificationRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.OrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.PaymentCompleteRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.SubscriptionPlanRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.CompleteAgreementResponse;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.dto.response.SimpleResponse;
import rs.ac.uns.ftn.paypal_service.exception.BadRequestException;
import rs.ac.uns.ftn.paypal_service.exception.NotFoundException;
import rs.ac.uns.ftn.paypal_service.exception.PayPalException;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.model.SubscriptionPlan;
import rs.ac.uns.ftn.paypal_service.model.TransactionAgreementData;
import rs.ac.uns.ftn.paypal_service.model.TransactionPaymentData;
import rs.ac.uns.ftn.paypal_service.model.TransactionPlanData;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;
import rs.ac.uns.ftn.paypal_service.repository.SubscriptionPlanRepository;
import rs.ac.uns.ftn.paypal_service.repository.TransactionAgreementRepository;
import rs.ac.uns.ftn.paypal_service.repository.TransactionPlanReposiotry;
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
	
	@Autowired
	private TransactionAgreementRepository transactionAgreementRepository;
	
	@Autowired
	private TransactionPlanReposiotry transactionPlanReposiotry;
	
	@Autowired
	private SubscriptionPlanRepository subscriptionPlanRepository;

	@Autowired
	private RestTemplate restTemplate;

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
		
		TransactionPaymentData transactionData;
		
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
	
	public Boolean createSubscriptionPlan(SubscriptionPlanRequest subscriptionPlanRequest) {
		
		validatePlanData(subscriptionPlanRequest);
		
		if(transactionPlanReposiotry.findByToken(subscriptionPlanRequest.getToken()) == null){
			throw new NotFoundException("Transaction token is not recognized ...");
		}
		
		TransactionPlanData transactionPlanData = new TransactionPlanData();
		transactionPlanData = transactionPlanReposiotry.findByToken(subscriptionPlanRequest.getToken());

		Plan plan = createPlan(subscriptionPlanRequest);
		
		PaypalPayment paymentR;
		if((paymentR = paymentRepository.findByUsername(transactionPlanData.getUsername())) == null ) {
			throw new NotFoundException("The payment information associated with that user does not exist");
		}

		APIContext context = createPaymentContext(paymentR.getPaymentId(), paymentR.getPaymentSecret());
		
		try {
			  // Create payment
			  Plan createdPlan = plan.create(context);

			  // Set up plan activate PATCH request
			  List<Patch> patchRequestList = new ArrayList<Patch>();
			  Map<String, String> value = new HashMap<String, String>();
			  value.put("state", "ACTIVE");

			  // Create update object to activate plan
			  Patch patch = new Patch();
			  patch.setPath("/");
			  patch.setValue(value);
			  patch.setOp("replace");
			  patchRequestList.add(patch);

			  // Activate plan
			  createdPlan.update(context, patchRequestList);
			  
			  SubscriptionPlan sb = new SubscriptionPlan();
			  sb.setName(createdPlan.getName());
			  sb.setFrequency(subscriptionPlanRequest.getFrequency());
			  sb.setFrequencyInterval(subscriptionPlanRequest.getFrequencyInterval());
			  sb.setPlanID(createdPlan.getId());
			  sb.setUsername(transactionPlanData.getUsername());
			  sb.setValue(subscriptionPlanRequest.getAmount());
			  sb.setDescription(subscriptionPlanRequest.getDescription());
			  sb.setCycles(subscriptionPlanRequest.getCycles());
			  sb.setType(subscriptionPlanRequest.getType());
			  subscriptionPlanRepository.save(sb);
			  transactionPlanData.setStatus("Completed");
			  transactionPlanReposiotry.save(transactionPlanData);
			} catch (PayPalRESTException e) {
				  transactionPlanData.setStatus("Canceled");
				  transactionPlanReposiotry.save(transactionPlanData);
				  System.err.println(e.getDetails());
			}
		
		return true;
	}
	
	public void deletePlan(Long id) {
    	if(id == null) {
    		throw new BadRequestException("Subscription plan id is not send.");
    	}
    	SubscriptionPlan plan = subscriptionPlanRepository.findById(id).get();
    	
    	
    	PaypalPayment paymentR;
		if((paymentR = paymentRepository.findByUsername(plan.getUsername())) == null ) {
			throw new NotFoundException("The payment information associated with that user does not exist");
		}

		APIContext context = createPaymentContext(paymentR.getPaymentId(), paymentR.getPaymentSecret());
    	
		List<Patch> patchRequestList = new ArrayList<Patch>();
		Map<String, String> value = new HashMap<String, String>();
		value.put("state", "DELETED");

		Patch patch = new Patch();
		patch.setPath("/");
		patch.setValue(value);
		patch.setOp("replace");
		patchRequestList.add(patch);
		
		Plan planNew = new Plan();
		planNew.setId(plan.getPlanID());
		try {
			planNew.update(context, patchRequestList);
		} catch (PayPalRESTException e) {
			// TODO Auto-generated catch block
			TransactionPlanData plan1 = transactionPlanReposiotry.findByUsername(plan.getUsername());
			plan1.setStatus("Deleted");
			transactionPlanReposiotry.save(plan1);
			throw new BadRequestException("Subscription plan id is not send. Failed to delete.");
		}
		
		subscriptionPlanRepository.deleteById(id);

	}
	
	public PaymentOrderResponse createAgreement(AgreementRequest agrementRequest) throws PayPalRESTException {

		Agreement agreement = makeAgreement(agrementRequest);
		
		PaypalPayment paymentR;
		if((paymentR = paymentRepository.findByUsername(agrementRequest.getUsername())) == null ) {
			throw new NotFoundException("The payment information associated with that user does not exist");
		}

		APIContext context = createPaymentContext(paymentR.getPaymentId(), paymentR.getPaymentSecret());
		String url = "";
		try {
			  agreement = agreement.create(context);

			  for (Links links : agreement.getLinks()) {
			    if ("approval_url".equals(links.getRel())) {
			      url = links.getHref();
			      String token = url.split("&")[1].split("=")[1];
			      TransactionAgreementData transAgr = new TransactionAgreementData();
			      transAgr.setToken(token);
			      transAgr.setUsername(agrementRequest.getUsername());
			      transAgr.setStatus("Created");
			      transAgr.setCallbackUrl(agrementRequest.getCallbackUrl());
			      transAgr.setOrderID(agrementRequest.getOrderId());
			      transAgr.setPlanID(agrementRequest.getPlanID());
			      SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
			      Date date = new Date(System.currentTimeMillis());
			      transAgr.setTime(formatter.format(date));
			      transactionAgreementRepository.save(transAgr);
			      break;
			    }
			  }
			} catch (PayPalRESTException e) {
			  System.err.println(e.getDetails());
			} catch (MalformedURLException e) {
			  e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
			  e.printStackTrace();
			}
		if (url.equals("")) {
			return new PaymentOrderResponse(false, url);
		}
		PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse(true, url);
		return paymentOrderResponse;
	}
	
	public CompleteAgreementResponse completeAgreement(AgreementCompleteRequest agreementCompleteRequest) {

		Agreement agreement =  new Agreement();
		agreement.setToken(agreementCompleteRequest.getToken());
		CompleteAgreementResponse completeAgreementResponse = new CompleteAgreementResponse(false,null);
		
		TransactionAgreementData transactionAgreementData;
		
		if((transactionAgreementData = transactionAgreementRepository.findByToken(agreementCompleteRequest.getToken())) == null) {
			throw new NotFoundException("Transaction link to that token not exist.");
		}

		PaypalPayment paymentR;
		if((paymentR = paymentRepository.findByUsername(transactionAgreementData.getUsername())) == null ) {
			throw new NotFoundException("The payment information associated with that user does not exist");
		}

		APIContext context = createPaymentContext(paymentR.getPaymentId(), paymentR.getPaymentSecret());

		try {
		  Agreement activeAgreement = agreement.execute(context, agreement.getToken());
		  completeAgreementResponse.setSuccess(true);
		  completeAgreementResponse.setAgreement(activeAgreement);
		  transactionAgreementData.setStatus("Completed");
		  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	      Date date = new Date(System.currentTimeMillis());
	      transactionAgreementData.setTime(formatter.format(date));
	      
	      /*NotificationRequest paymentNotificationDto = new NotificationRequest(transactionAgreementData.getOrderID(), TransactionStatus.SUCCESS);

	      SimpleResponse simpleResponseDTO = restTemplate.postForObject(transactionAgreementData.getCallbackUrl(), paymentNotificationDto, SimpleResponse.class);

	      if(simpleResponseDTO != null && simpleResponseDTO.getSuccess()){
	    	  transactionAgreementData.setNotification("NOTIFIED");
	      }*/
	      transactionAgreementRepository.save(transactionAgreementData);
	      
		} catch (PayPalRESTException e) {
		  System.err.println(e.getDetails());
		}
		
		
		return completeAgreementResponse;
	}
	
	private Agreement makeAgreement(AgreementRequest agremeRequest) {
		// Create new agreement
		Agreement agreement = new Agreement();
		agreement.setName("Base Agreement");
		agreement.setDescription("Basic Agreement");
		//this format is ISO 8601 standard .. paypal required it ...
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date date = new Date(System.currentTimeMillis()+100000);
		agreement.setStartDate(formatter.format(date));

		// Set plan ID
		Plan plan = new Plan();
		plan.setId(agremeRequest.getPlanID());
		agreement.setPlan(plan);

		// Add payer details
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		agreement.setPayer(payer);

		// Set shipping address information
		ShippingAddress shipping = new ShippingAddress();
		shipping.setLine1("111 First Street");
		shipping.setCity("Saratoga");
		shipping.setState("CA");
		shipping.setPostalCode("95070");
		shipping.setCountryCode("US");
		agreement.setShippingAddress(shipping);
		return agreement;
	}
	
	public Boolean cancelTransaction(String token) {
		TransactionPaymentData transactionData;
		if((transactionData = transactionRepository.findByToken(token)) == null) {
			throw new NotFoundException("Transaction link to that token not exist.");
		}
		transactionData.setStatus("Canceled");
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		transactionData.setTime(formatter.format(date));
		System.out.println("USAO SAM MALO DA POGLEDAM SAMOOO :D");
		
		NotificationRequest paymentNotificationDto = new NotificationRequest(transactionData.getOrderID(), TransactionStatus.FAILED);

		SimpleResponse simpleResponseDTO = restTemplate.postForObject(transactionData.getCallbackUrl(), paymentNotificationDto, SimpleResponse.class);

		if(simpleResponseDTO != null && simpleResponseDTO.getSuccess()){
			transactionData.setNotification("NOTIFIED");
		}
		
		transactionData = transactionRepository.save(transactionData);
		if(transactionData != null)
			return true;
		else
			return false;
	}
	
	public Boolean cancelAgreement(String token) {
		TransactionAgreementData transactionData;
		if((transactionData = transactionAgreementRepository.findByToken(token)) == null) {
			throw new NotFoundException("Transaction link to that token not exist.");
		}
		transactionData.setStatus("Canceled");
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		transactionData.setTime(formatter.format(date));
		
		/*NotificationRequest paymentNotificationDto = new NotificationRequest(transactionData.getOrderID(), TransactionStatus.FAILED);

		SimpleResponse simpleResponseDTO = restTemplate.postForObject(transactionData.getCallbackUrl(), paymentNotificationDto, SimpleResponse.class);

		if(simpleResponseDTO != null && simpleResponseDTO.getSuccess()){
			transactionData.setNotification("NOTIFIED");
		}*/
		
		transactionData = transactionAgreementRepository.save(transactionData);
		if(transactionData != null)
			return true;
		else
			return false;
	}
	
	private Plan createPlan(SubscriptionPlanRequest subscriptionPlanRequest) {
		// Build Plan object
		
		Plan plan = new Plan();
		plan.setName(subscriptionPlanRequest.getName());
		plan.setDescription(subscriptionPlanRequest.getDescription());
		plan.setType("fixed");

		// Payment_definitions
		PaymentDefinition paymentDefinition = new PaymentDefinition();
		paymentDefinition.setName("Regular Payments");
		paymentDefinition.setType("REGULAR");
		paymentDefinition.setFrequency(subscriptionPlanRequest.getFrequency());
		paymentDefinition.setFrequencyInterval(subscriptionPlanRequest.getFrequencyInterval());
		paymentDefinition.setCycles(Integer.toString(subscriptionPlanRequest.getCycles()));

		// Currency
		Currency currency = new Currency();
		currency.setCurrency("USD");
		currency.setValue(Double.toString(subscriptionPlanRequest.getAmount()));
		paymentDefinition.setAmount(currency);

		// Charge_models
		ChargeModels chargeModels = new com.paypal.api.payments.ChargeModels();
		chargeModels.setType("SHIPPING");
		chargeModels.setAmount(currency);
		List<ChargeModels> chargeModelsList = new ArrayList<ChargeModels>();
		chargeModelsList.add(chargeModels);
		paymentDefinition.setChargeModels(chargeModelsList);

		// Payment_definition
		List<PaymentDefinition> paymentDefinitionList = new ArrayList<PaymentDefinition>();
		paymentDefinitionList.add(paymentDefinition);
		plan.setPaymentDefinitions(paymentDefinitionList);

		// Merchant_preferences
		MerchantPreferences merchantPreferences = new MerchantPreferences();
		merchantPreferences.setSetupFee(currency);
		merchantPreferences.setCancelUrl("https://localhost:4201/cancelAgreement");
		merchantPreferences.setReturnUrl("https://localhost:4201/successAgreement");
		merchantPreferences.setMaxFailAttempts("0");
		merchantPreferences.setAutoBillAmount("YES");
		merchantPreferences.setInitialFailAmountAction("CONTINUE");
		plan.setMerchantPreferences(merchantPreferences);
		
		return plan;
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
	
	private void validatePlanData(SubscriptionPlanRequest subscriptionPlanRequest) {
		if (subscriptionPlanRequest == null) {
			throw new BadRequestException("There is not subscription plan info.");
		}
		if(subscriptionPlanRequest.getToken() == null ||
		   subscriptionPlanRequest.getDescription() == null ||
		   subscriptionPlanRequest.getName() == null ||
		   subscriptionPlanRequest.getAmount() == null ||
		   subscriptionPlanRequest.getCycles() == 0 ||
		   subscriptionPlanRequest.getFrequency() == null ||
		   subscriptionPlanRequest.getFrequencyInterval() == null){
			throw new BadRequestException("Plan information is not sent");
		}

	}	
	 
	private void createTransaction(PaymentOrderResponse paymentOrderResponse, OrderRequest orderRequest) {
		String token = paymentOrderResponse.getUrl().split("&")[1].split("=")[1];
		TransactionPaymentData transaction = new TransactionPaymentData();
		transaction.setToken(token);
		transaction.setCallbackUrl(orderRequest.getCallbackUrl());
		transaction.setOrderID(orderRequest.getOrderId());
		transaction.setUsername(orderRequest.getUsername());
		transaction.setPrice(orderRequest.getTotalPrice());
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		transaction.setTime(formatter.format(date));
		transaction.setStatus("Created");
	    transactionRepository.save(transaction);
	}
	
	private void completeTransaction(Payment payment, TransactionPaymentData transactionData) {
		transactionData.setStatus("Completed");
		transactionData.setBuyer(payment.getPayer().getPayerInfo().getShippingAddress().getRecipientName());
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		transactionData.setTime(formatter.format(date));
		
		NotificationRequest paymentNotificationDto = new NotificationRequest(transactionData.getOrderID(), TransactionStatus.SUCCESS);

		SimpleResponse simpleResponseDTO = restTemplate.postForObject(transactionData.getCallbackUrl(), paymentNotificationDto, SimpleResponse.class);

		if(simpleResponseDTO != null && simpleResponseDTO.getSuccess()){
			transactionData.setNotification("NOTIFIED");
		}
		
		transactionRepository.save(transactionData);
	}
}
