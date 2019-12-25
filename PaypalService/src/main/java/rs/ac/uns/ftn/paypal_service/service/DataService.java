package rs.ac.uns.ftn.paypal_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.paypal_service.dto.request.UserDataRequest;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;

@Service
public class DataService {

	@Autowired
    private PaymentRepository paymentRepository;
	
	//cuvanje podataka vezanih za prodavca .....
	public Boolean saveInfo(UserDataRequest userDataRequest) {
		PaypalPayment payment = new PaypalPayment();
		payment.setPaymentId(userDataRequest.getId());
		payment.setPaymentSecret(userDataRequest.getSecret());
		payment.setUsername(userDataRequest.getUsername());
		
		payment = paymentRepository.save(payment);
		
		if(payment != null) {
			return true;
		}else {
			return false;
		}
		
	}
}
