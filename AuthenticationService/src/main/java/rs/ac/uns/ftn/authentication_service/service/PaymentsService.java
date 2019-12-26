package rs.ac.uns.ftn.authentication_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
			return lista;
		}else {
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
		}else {
			payment = new PaymentResponse(null, null);
		}
		
		
		return payment;
	}
	
	//cuvanje podataka vezanih za uplatu od strane kupca....
	public String getTransactionLink(TransactionRequest transactionRequest) {
		
		Transaction transaction = new Transaction();
		transaction.setEmail(transactionRequest.getEmail());
		transaction.setTotalPrice(transactionRequest.getTotalPrice());
		transaction.setUuid(UUID.randomUUID().toString());
		transaction = transactionRepository.save(transaction);
		if(transaction != null)
			return "http://localhost:4200/payingType/"+ transaction.getUuid();
		else
			return "";
	}
	
	public PaymentLinkResponse getPaymentLink(PaymentLinkRequest paymentLinkRequest) {
		Transaction transaction = new Transaction();
		transaction = transactionRepository.findByUuid(paymentLinkRequest.getToken());
		Client client = new Client();
		client = clientRepository.findByEmail(transaction.getEmail());
		PaymentOrderRequest order = new PaymentOrderRequest();
		order.setTotalPrice(transaction.getTotalPrice());
		order.setUsername(client.getUsername());
		
		ResponseEntity<PaymentLinkResponse> response = restTemplate.postForEntity("http://localhost:8765/api-"+paymentLinkRequest.getType().toLowerCase()+"/pay", order, PaymentLinkResponse.class);
		
		
		return response.getBody();
		
	}
	
}
