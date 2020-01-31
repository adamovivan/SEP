package rs.ac.uns.ftn.paypal_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.base.rest.PayPalRESTException;

import rs.ac.uns.ftn.paypal_service.dto.request.OrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.TransactionPlanLinkRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.UserPlansRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.dto.response.SubscriptionPlanResponse;
import rs.ac.uns.ftn.paypal_service.dto.response.TransactionPlanLinkResponse;
import rs.ac.uns.ftn.paypal_service.exception.BadRequestException;
import rs.ac.uns.ftn.paypal_service.exception.NotFoundException;
import rs.ac.uns.ftn.paypal_service.model.PaypalPayment;
import rs.ac.uns.ftn.paypal_service.model.SubscriptionPlan;
import rs.ac.uns.ftn.paypal_service.model.TransactionPlanData;
import rs.ac.uns.ftn.paypal_service.repository.PaymentRepository;
import rs.ac.uns.ftn.paypal_service.repository.SubscriptionPlanRepository;
import rs.ac.uns.ftn.paypal_service.repository.TransactionPlanReposiotry;

@Service
public class PaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private TransactionPlanReposiotry transactionPlanReposiotry;
    
    @Autowired
    private PaypalService paypalService;
    
    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    public PaymentOrderResponse createOrder(PaymentOrderRequest paymentOrderRequest){
    	
    	validate(paymentOrderRequest);
        
        PaypalPayment payment = paymentRepository.findByUsername(paymentOrderRequest.getUsername());
        
        OrderRequest orderDTO = orderDTOCreate(payment, paymentOrderRequest);

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
    
    private OrderRequest orderDTOCreate(PaypalPayment paypalInfo, PaymentOrderRequest info) {
    	OrderRequest orderDTO = new OrderRequest();
    	orderDTO.setUsername(info.getUsername());
        orderDTO.setTotalPrice(info.getTotalPrice());
        orderDTO.setCallbackUrl(info.getCallbackUrl());
        orderDTO.setOrderId(info.getOrderId());
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
		if(paymentOrderRequest.getCallbackUrl() == null || paymentOrderRequest.getOrderId() == null) {
			throw new BadRequestException("Payment order info is not send.");
		}
		if(paymentRepository.findByUsername(paymentOrderRequest.getUsername()) == null){
			throw new NotFoundException("Seller does not exist.");
		}
		if(paymentOrderRequest.getTotalPrice() < 0.0) {
			throw new BadRequestException("You cannot pay with negative value.");
		}
	}
    
    public TransactionPlanLinkResponse getTransactionPlanLink(TransactionPlanLinkRequest transactionPlanLinkRequest) {
		
		TransactionPlanData transaction = new TransactionPlanData();
		transaction.setUsername(transactionPlanLinkRequest.getUsername());
		transaction.setToken(UUID.randomUUID().toString());
		transaction.setStatus("Created");
		if(transactionPlanReposiotry.findByUsername(transactionPlanLinkRequest.getUsername()) != null) 
			transaction = transactionPlanReposiotry.findByUsername(transactionPlanLinkRequest.getUsername());
		else
			transaction = transactionPlanReposiotry.save(transaction);
		TransactionPlanLinkResponse response = new TransactionPlanLinkResponse();
		if(transaction != null) {
			response.setSuccess(true);
			response.setUrl("https://localhost:4201/subscriptionPlan/"+ transaction.getToken());
			logger.info("Successfully obtained transaction link");
		}else {
			response.setSuccess(false);
			response.setUrl("");
			logger.info("No transaction link was obtained");
		}
		return response;
	}
    
    public List<SubscriptionPlan> getSubscriptionPlans(TransactionPlanLinkRequest transactionPlanLinkRequest) {
		
    	List<SubscriptionPlan> list = new ArrayList<>();
    	if(transactionPlanLinkRequest == null) {
    		throw new BadRequestException("You cannot pay with negative value.");
    	}
    	if(subscriptionPlanRepository.findByUsername(transactionPlanLinkRequest.getUsername()) == null) {
    		throw new NotFoundException("There is not created plans");
    	}
    	
    	list = subscriptionPlanRepository.findByUsername(transactionPlanLinkRequest.getUsername());
    	System.out.println(">>>" + list + "<<<");
    	return list;
	}
    
    public SubscriptionPlanResponse  getSubscriptionPlansByUsername(UserPlansRequest userPlansRequest)  {
    	if(userPlansRequest == null) {
    		throw new BadRequestException("User plan request is not send.");
    	}
    	if(userPlansRequest.getUsername() == null || userPlansRequest.getType() == null) {
    		throw new BadRequestException("User plan data is not send.");
    	}
    	List<SubscriptionPlan> list = subscriptionPlanRepository.findByUsernameAndType(userPlansRequest.getUsername(),userPlansRequest.getType());
    	SubscriptionPlanResponse spr = new SubscriptionPlanResponse();
    	spr.setPlans(list);
    	return spr;
    }
    
    
}
