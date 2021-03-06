package rs.ac.uns.ftn.authentication_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.ac.uns.ftn.authentication_service.model.AgreementTransaction;
import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.model.PaymentTransaction;
import rs.ac.uns.ftn.authentication_service.model.Payments;
import rs.ac.uns.ftn.authentication_service.repository.ClientRepository;
import rs.ac.uns.ftn.authentication_service.repository.PaymentsRepository;
import rs.ac.uns.ftn.authentication_service.repository.TransactionAgreementRepository;
import rs.ac.uns.ftn.authentication_service.repository.TransactionRepository;
import rs.ac.uns.ftn.authentication_service.request.AgreementRequest;
import rs.ac.uns.ftn.authentication_service.request.AgreementUrlRequest;
import rs.ac.uns.ftn.authentication_service.request.PaymentLinkRequest;
import rs.ac.uns.ftn.authentication_service.request.PaymentOrderRequest;
import rs.ac.uns.ftn.authentication_service.request.PaymentRequest;
import rs.ac.uns.ftn.authentication_service.request.TransactionAgreementRequest;
import rs.ac.uns.ftn.authentication_service.request.TransactionPlanRequest;
import rs.ac.uns.ftn.authentication_service.request.TransactionRequest;
import rs.ac.uns.ftn.authentication_service.request.UserPlansRequest;
import rs.ac.uns.ftn.authentication_service.response.PaymentLinkResponse;
import rs.ac.uns.ftn.authentication_service.response.PaymentResponse;
import rs.ac.uns.ftn.authentication_service.response.SubscriptionPlanResponse;

@Service
public class PaymentsService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentsService.class);
	
	@Autowired
	private PaymentsRepository paymentsRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionAgreementRepository transactionAgreementRepository;
	
	
	
	@Autowired
	 private RestTemplate restTemplate;
	
	//dodavanje paymenta za prodavca
	public Boolean addPayments(PaymentRequest paymentRequest) {
		Payments payments = new Payments();
		payments = paymentsRepository.findByUsername(paymentRequest.getUsername());
		if(payments != null) {
			String s = payments.getPayments();
			s = s + "," + paymentRequest.getPayment();
			payments.setPayments(s);
			paymentsRepository.save(payments);
			return true;
		}else {
			Payments payment = new Payments();
			payment.setUsername(paymentRequest.getUsername());
			payment.setPayments(paymentRequest.getPayment());
			paymentsRepository.save(payment);
			return true;
		}
	}
	//dobavljanje liste odabranih nacina placanja prodavca
	public List<String> getPayments(String username) {
		List<String> lista = new ArrayList<String>();
		Payments payments = new Payments();
		payments = paymentsRepository.findByUsername(username);
		if(payments != null) {
			String s = payments.getPayments();
			String st[] = s.split(",");
			for (String string : st) {
				lista.add(string);
			}
			logger.info("Successfully obtained selected payment methods for the " + username + " user");
			return lista;
		}else {
			logger.error("Selected payment methods for " + username + " users have not been received");
			return lista;
		}
	}
	//dobavljanje liste odabranih nacina placanja prodavca sa username-om
	public PaymentResponse getTypePayments(String token) {
		List<String> lista = new ArrayList<>();
		
		PaymentTransaction transaction = new PaymentTransaction();
		transaction = transactionRepository.findByUuid(token);
		System.out.println(transaction.getEmail());
		Client client = new Client();
		client = clientRepository.findByEmail(transaction.getEmail());
		PaymentResponse payment;
		Payments payments = new Payments();
		payments = paymentsRepository.findByUsername(client.getUsername());
		if(payments != null) {
			String st[] = payments.getPayments().split(",");
			for (String string : st) {
				lista.add(string);
			}
			payment = new PaymentResponse(payments.getUsername(), lista);
			logger.info("Successfully obtained selected payment methods for the " + client.getUsername() + " user");
		}else {
			payment = new PaymentResponse(null, null);
			logger.error("Selected payment methods for " + client.getUsername() + " users have not been received");
		}
		
		
		return payment;
	}
	
	public SubscriptionPlanResponse getSubscriptionPlans(String token) {
		if(token == null) {
			return new SubscriptionPlanResponse();
		}
		if(transactionAgreementRepository.findByUuid(token) == null) {
			return new SubscriptionPlanResponse();
		}
		AgreementTransaction transaction = transactionAgreementRepository.findByUuid(token);
		if(clientRepository.findByEmail(transaction.getEmail()) == null) {
			return new SubscriptionPlanResponse();
		}
	    Client client = clientRepository.findByEmail(transaction.getEmail());
	    
	    UserPlansRequest upr = new UserPlansRequest();
	    upr.setType(transaction.getType());
	    upr.setUsername(client.getUsername());
	    try {
	    	ResponseEntity<SubscriptionPlanResponse> spr = restTemplate.postForEntity("https://localhost:8765/api-paypal/getSubscriptionPlansByUsername", upr, SubscriptionPlanResponse.class);
	    	return spr.getBody();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	    
		
		return new SubscriptionPlanResponse();
	}
	
	//cuvanje podataka vezanih za uplatu od strane kupca....
	public PaymentLinkResponse getTransactionLink(TransactionRequest transactionRequest) {
		
		PaymentTransaction transaction = new PaymentTransaction();
		transaction.setEmail(transactionRequest.getEmail());
		transaction.setOrderId(transactionRequest.getOrderId());
		transaction.setCallbackUrl(transactionRequest.getCallbackUrl());
		transaction.setTotalPrice(transactionRequest.getTotalPrice());
		transaction.setUuid(UUID.randomUUID().toString());
		transaction = transactionRepository.save(transaction);
		PaymentLinkResponse response = new PaymentLinkResponse();
		if(transaction != null) {
			response.setSuccess(true);
			response.setUrl("https://localhost:4200/payment-method/"+ transaction.getUuid());
			logger.info("Successfully obtained transaction link");
			return response;
		}else {
			response.setSuccess(false);
			response.setUrl("");
			logger.info("No transaction link was obtained");
			return response;
		}
		
	}
	
	//cuvanje podataka vezanih za uplatu od strane kupca....
		public PaymentLinkResponse getTransactionAgreementLink(TransactionAgreementRequest transactionAgreementRequest) {
			
			AgreementTransaction transaction = new AgreementTransaction();
			transaction.setEmail(transactionAgreementRequest.getEmail());
			transaction.setUuid(UUID.randomUUID().toString());
			transaction.setType(transactionAgreementRequest.getType());
			transaction.setCallbackUrl(transactionAgreementRequest.getCallbackUrl());
			transaction.setOrderId(transactionAgreementRequest.getOrderId());
			transaction = transactionAgreementRepository.save(transaction);
			PaymentLinkResponse response = new PaymentLinkResponse();
			if(transaction != null) {
				response.setSuccess(true);
				response.setUrl("https://localhost:4200/subscription-agreement/"+ transaction.getUuid());
				logger.info("Successfully obtained transaction link");
				return response;
			}else {
				response.setSuccess(false);
				response.setUrl("");
				logger.info("No transaction link was obtained");
				return response;
			}
			
		}
	
	public PaymentLinkResponse getTransactionPlanLink(TransactionPlanRequest transactionPlanRequest) {
		try {
			PaymentLinkResponse p = restTemplate.postForObject("https://localhost:8765/api-paypal/getTransactionPlanLink", transactionPlanRequest, PaymentLinkResponse.class);
	    	return p;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new PaymentLinkResponse();
	}
	
	public PaymentLinkResponse getPaymentLink(PaymentLinkRequest paymentLinkRequest) {
		PaymentTransaction transaction = new PaymentTransaction();
		transaction = transactionRepository.findByUuid(paymentLinkRequest.getToken());
		Client client = new Client();
		client = clientRepository.findByEmail(transaction.getEmail());
		PaymentOrderRequest order = new PaymentOrderRequest();
		order.setTotalPrice(transaction.getTotalPrice());
		order.setUsername(client.getUsername());
		order.setOrderId(transaction.getOrderId());
		order.setCallbackUrl(transaction.getCallbackUrl());
		try {
			ResponseEntity<PaymentLinkResponse> response = restTemplate.postForEntity("https://localhost:8765/api-"+paymentLinkRequest.getType().toLowerCase()+"/pay", order, PaymentLinkResponse.class);
			return response.getBody();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		return new PaymentLinkResponse();
		
		
	}
	
	public PaymentLinkResponse getAgreementLink(AgreementRequest agreementRequest) {
		AgreementTransaction transaction = new AgreementTransaction();
		transaction = transactionAgreementRepository.findByUuid(agreementRequest.getToken());
		Client client = new Client();
		client = clientRepository.findByEmail(transaction.getEmail());
		
		AgreementUrlRequest aur = new AgreementUrlRequest(client.getUsername(),agreementRequest.getPlanID(),
				transaction.getOrderId(),transaction.getCallbackUrl());
		
		try {
			ResponseEntity<PaymentLinkResponse> response = restTemplate.postForEntity("https://localhost:8765/api-paypal/createAgreement", aur, PaymentLinkResponse.class);
			return response.getBody();
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		return new PaymentLinkResponse();
	}
	
}
