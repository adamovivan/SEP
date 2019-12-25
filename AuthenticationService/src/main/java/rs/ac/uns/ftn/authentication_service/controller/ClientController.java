package rs.ac.uns.ftn.authentication_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.request.LoginRequest;
import rs.ac.uns.ftn.authentication_service.request.PaymentLinkRequest;
import rs.ac.uns.ftn.authentication_service.request.PaymentRequest;
import rs.ac.uns.ftn.authentication_service.request.TransactionRequest;
import rs.ac.uns.ftn.authentication_service.response.LoginResponse;
import rs.ac.uns.ftn.authentication_service.response.PaymentResponse;
import rs.ac.uns.ftn.authentication_service.service.LoginService;
import rs.ac.uns.ftn.authentication_service.service.PaymentsService;
import rs.ac.uns.ftn.authentication_service.service.RegistrationService;

@RestController
@CrossOrigin(origins = "", allowedHeaders = "", maxAge = 3600)
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

	@Autowired
	RegistrationService registrationService;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	PaymentsService paymentsService;

	@PostMapping("/registerClient")
	public ResponseEntity<Client> saveKorisnik(@RequestBody Client client) throws Exception{
		return new ResponseEntity<Client>(registrationService.save(client), HttpStatus.OK);
	}
	
	@PostMapping("/loginClient")
	public ResponseEntity<LoginResponse> loginKorisnik(@RequestBody LoginRequest loginRequest) throws Exception{
		return new ResponseEntity<LoginResponse>(loginService.login(loginRequest), HttpStatus.OK);
	}
	
	//seller
	@PostMapping(value = "/addPayments")
	public ResponseEntity<Boolean> addPayments(@RequestBody PaymentRequest paymentRequest) {
		return new ResponseEntity<Boolean>(paymentsService.addPayments(paymentRequest), HttpStatus.OK);
	}
	//seller
	@GetMapping(value = "/getPayments/{username}")
	public ResponseEntity<List<String>> getPayments(@PathVariable String username) {
		return new ResponseEntity<List<String>>(paymentsService.getPayments(username), HttpStatus.OK);
	}

	//buyer
	@GetMapping(value = "/getTypePayments/{token}")
	public ResponseEntity<PaymentResponse> getTypePayments(@PathVariable String token) {
		return new ResponseEntity<PaymentResponse>(paymentsService.getTypePayments(token), HttpStatus.OK);
	}
	
	@PostMapping(value = "/getTransactionLink")
	public ResponseEntity<String> getTransactionLink(@RequestBody TransactionRequest transactionRequest) {
		return new ResponseEntity<String>(paymentsService.getTransactionLink(transactionRequest), HttpStatus.OK);
	}
	
	@PostMapping(value = "/getPaymentLink")
	public ResponseEntity<String> getPaymentLink(@RequestBody PaymentLinkRequest paymentLinkRequest) {
		return new ResponseEntity<String>(paymentsService.getPaymentLink(paymentLinkRequest), HttpStatus.OK);
	}
}
