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

import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.model.Payments;
import rs.ac.uns.ftn.authentication_service.model.Transaction;
import rs.ac.uns.ftn.authentication_service.repository.ClientRepository;
import rs.ac.uns.ftn.authentication_service.repository.PaymentsRepository;
import rs.ac.uns.ftn.authentication_service.repository.TransactionRepository;
import rs.ac.uns.ftn.authentication_service.request.PaymentLinkRequest;
import rs.ac.uns.ftn.authentication_service.request.PaymentOrderRequest;
import rs.ac.uns.ftn.authentication_service.request.PaymentRequest;
import rs.ac.uns.ftn.authentication_service.request.TransactionRequest;
import rs.ac.uns.ftn.authentication_service.response.PaymentLinkResponse;
import rs.ac.uns.ftn.authentication_service.response.PaymentResponse;

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
		
		Transaction transaction = new Transaction();
		transaction = transactionRepository.findByUuid(token);
		
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
	
	//cuvanje podataka vezanih za uplatu od strane kupca....
	public PaymentLinkResponse getTransactionLink(TransactionRequest transactionRequest) {
		
		Transaction transaction = new Transaction();
		transaction.setEmail(transactionRequest.getEmail());
		transaction.setTotalPrice(transactionRequest.getTotalPrice());
		transaction.setUuid(UUID.randomUUID().toString());
		transaction = transactionRepository.save(transaction);
		PaymentLinkResponse response = new PaymentLinkResponse();
		if(transaction != null) {
			response.setSuccess(true);
			response.setUrl("https://localhost:4200/payingType/"+ transaction.getUuid());
			logger.info("Successfully obtained transaction link");
			return response;
		}else {
			response.setSuccess(false);
			response.setUrl("");
			logger.info("No transaction link was obtained");
			return response;
		}
	}
	
	public PaymentLinkResponse getPaymentLink(PaymentLinkRequest paymentLinkRequest) {
		Transaction transaction = new Transaction();
		transaction = transactionRepository.findByUuid(paymentLinkRequest.getToken());
		Client client = new Client();
		client = clientRepository.findByEmail(transaction.getEmail());
		PaymentOrderRequest order = new PaymentOrderRequest();
		order.setTotalPrice(transaction.getTotalPrice());
		order.setUsername(client.getUsername());
		try {
			ResponseEntity<PaymentLinkResponse> response = restTemplate.postForEntity("https://localhost:8765/api-"+paymentLinkRequest.getType().toLowerCase()+"/pay", order, PaymentLinkResponse.class);
			logger.info("Successfully obtained payment link for payment to" + client.getUsername() + " user account");
			return response.getBody();
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("no payment link was provided for payment to " + client.getUsername() + "'s account");
			// TODO: handle exception
		}
		
		
		return new PaymentLinkResponse();
		
		
	}
	
}
