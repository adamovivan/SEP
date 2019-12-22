package rs.ac.uns.ftn.paypal_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.paypal.base.rest.PayPalRESTException;

import rs.ac.uns.ftn.paypal_service.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.model.Order;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private PaypalService paypalService;

    @Value("${zuul-gateway-url}")
    private String zuulUrl;

    public PaymentOrderResponse createOrder(PaymentOrderRequest paymentOrderRequest){
    	
        if(paymentOrderRequest.getUsername() != null){
        
	        PaypalPayment payment = paymentRepository.findByUsername(paymentOrderRequest.getUsername());
	        Order orderDTO = new Order();
	        orderDTO.setTotalPrice(paymentOrderRequest.getTotalPrice());
	        orderDTO.setClientId(payment.getPaymentId());
	        orderDTO.setClientSecret(payment.getPaymentSecret());
	        orderDTO.setSuccessUrl(payment.getSuccessUrl());
	        orderDTO.setCancelUrl(payment.getCancelUrl());
	
			try {
				return paypalService.createPayment(orderDTO);
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
			
        }
        	
        return new PaymentOrderResponse(false, "");
        
    }
}
