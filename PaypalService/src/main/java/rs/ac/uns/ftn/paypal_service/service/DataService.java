package rs.ac.uns.ftn.paypal_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.paypal_service.dto.request.UserDataRequest;
import rs.ac.uns.ftn.paypal_service.exception.BadRequestException;
import rs.ac.uns.ftn.paypal_service.exception.ExceptionResolver;
import rs.ac.uns.ftn.paypal_service.exception.NotFoundException;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;

@Service
public class DataService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

	@Autowired
    private PaymentRepository paymentRepository;
	
	public Boolean saveInfo(UserDataRequest userDataRequest) {
		validate(userDataRequest);
		PaypalPayment payment = paymentInfo(userDataRequest);
		payment = paymentRepository.save(payment);
		
		if(payment == null) {
			throw new NotFoundException("Seller " + userDataRequest.getUsername() + " did not save his payment information. ");
		}
		
		logger.info("Seller " + userDataRequest.getUsername() + " successfully saved his payment information. " );
		return true;
		
	}
	
	private PaypalPayment paymentInfo(UserDataRequest userDataRequest) {
		PaypalPayment payment = new PaypalPayment();
		payment.setPaymentId(userDataRequest.getId());
		payment.setPaymentSecret(userDataRequest.getSecret());
		payment.setUsername(userDataRequest.getUsername());
		return payment;
	}
	
	private void validate(UserDataRequest userDataRequest) {
		if(userDataRequest == null) {
			throw new BadRequestException("User payment info is not found.");
		}
		if(userDataRequest.getId() == null) {
			throw new BadRequestException("Payment id is not set.");
		}
		if(userDataRequest.getSecret() == null) {
			throw new BadRequestException("Payment secret is not set.");
		}
		if(userDataRequest.getUsername() == null) {
			throw new BadRequestException("Username is not set.");
		}
	}
}
