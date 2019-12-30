package rs.ac.uns.ftn.paypal_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.base.rest.PayPalRESTException;

import rs.ac.uns.ftn.paypal_service.dto.request.OrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.exception.BadRequestException;
import rs.ac.uns.ftn.paypal_service.exception.NotFoundException;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;

@Service
public class PaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private PaypalService paypalService;

    public PaymentOrderResponse createOrder(PaymentOrderRequest paymentOrderRequest){
    	
    	validate(paymentOrderRequest);
        
        PaypalPayment payment = paymentRepository.findByUsername(paymentOrderRequest.getUsername());
        
        OrderRequest orderDTO = orderDTOCreate(payment, paymentOrderRequest.getTotalPrice(), paymentOrderRequest.getUsername());

		try {
			PaymentOrderResponse response = paypalService.createPayment(orderDTO);
			logger.info("Redirect url for paypal payment to user account " + paymentOrderRequest.getUsername() + " was"
					+ " successfully created.");
			return response;
		} catch (PayPalRESTException e) {
			logger.error("Redirect url for paypal payment to user account " + paymentOrderRequest.getUsername() + " was"
					+ " not created.");
			e.printStackTrace();
		}
        return new PaymentOrderResponse(false, "");
        
    }
    
    private OrderRequest orderDTOCreate(PaypalPayment paypalInfo, Double totalPrice, String username) {
    	OrderRequest orderDTO = new OrderRequest();
    	orderDTO.setUsername(username);
        orderDTO.setTotalPrice(totalPrice);
        orderDTO.setClientId(paypalInfo.getPaymentId());
        orderDTO.setClientSecret(paypalInfo.getPaymentSecret());
        orderDTO.setSuccessUrl("https://localhost:4201/success");
        orderDTO.setCancelUrl("https://localhost:4201/cancel");
        return orderDTO;
    }
    
    private void validate(PaymentOrderRequest paymentOrderRequest) {
		if(paymentOrderRequest == null) {
			throw new BadRequestException("Payment order request is not send.");
		}
		if(paymentRepository.findByUsername(paymentOrderRequest.getUsername()) == null){
			throw new NotFoundException("Seller does not exist.");
		}if(paymentOrderRequest.getTotalPrice() < 0.0) {
			throw new BadRequestException("You cannot pay with negative value.");
		}
	}
    
    
}
