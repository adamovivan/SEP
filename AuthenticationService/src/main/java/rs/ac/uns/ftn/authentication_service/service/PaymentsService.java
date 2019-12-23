package rs.ac.uns.ftn.authentication_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.authentication_service.model.Payments;
import rs.ac.uns.ftn.authentication_service.repository.PaymentsRepository;
import rs.ac.uns.ftn.authentication_service.request.PaymentRequest;
import rs.ac.uns.ftn.authentication_service.request.UserPaymentsRequest;

@Service
public class PaymentsService {
	
	@Autowired
	private PaymentsRepository paymentsRepository;
	
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
}
