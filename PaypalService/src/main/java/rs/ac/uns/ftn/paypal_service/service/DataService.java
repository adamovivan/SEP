package rs.ac.uns.ftn.paypal_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.paypal_service.dto.request.UserDataRequest;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;

@Service
public class DataService {

	@Autowired
    private PaymentRepository paymentRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(DataService.class);
	
	//cuvanje podataka vezanih za prodavca .....
	public Boolean saveInfo(UserDataRequest userDataRequest) {
		PaypalPayment payment = new PaypalPayment();
		payment.setPaymentId(userDataRequest.getId());
		payment.setPaymentSecret(userDataRequest.getSecret());
		payment.setUsername(userDataRequest.getUsername());
		
		payment = paymentRepository.save(payment);
		
		if(payment != null) {
			logger.info("Seller " + userDataRequest.getUsername() + " successfully saved his payment information. " );
			return true;
		}else {
			logger.error("Seller " + userDataRequest.getUsername() + " did not save his payment information. " );
			return false;
		}
		
	}
}
