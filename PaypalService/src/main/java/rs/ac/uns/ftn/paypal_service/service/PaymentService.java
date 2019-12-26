package rs.ac.uns.ftn.paypal_service.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.paypal.base.rest.PayPalRESTException;

import rs.ac.uns.ftn.paypal_service.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.model.Order;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.model.TransactionData;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;
import rs.ac.uns.ftn.paypal_service.repository.TransactionRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
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
	        orderDTO.setSuccessUrl("http://localhost:4201/success");
	        orderDTO.setCancelUrl("http://localhost:4201/cancel");
	
			try {
				PaymentOrderResponse response = paypalService.createPayment(orderDTO);
				TransactionData transaction = new TransactionData();
				transaction.setStatus("created");
				transaction.setPrice(paymentOrderRequest.getTotalPrice());
				transaction.setUsername(paymentOrderRequest.getUsername());
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
				Date date = new Date(System.currentTimeMillis());
				transaction.setTime(formatter.format(date));
				transaction = transactionRepository.save(transaction);
				return response;
			} catch (PayPalRESTException e) {
				e.printStackTrace();
			}
			
        }
        	
        return new PaymentOrderResponse(false, "");
        
    }
}
