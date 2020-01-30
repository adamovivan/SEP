package rs.ac.uns.ftn.paypal_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.paypal_service.dto.request.PaymentCompleteRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.SubscriptionPlanRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.TransactionPlanLinkRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.UserDataRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.dto.response.TransactionPlanLinkResponse;
import rs.ac.uns.ftn.paypal_service.model.SubscriptionPlan;
import rs.ac.uns.ftn.paypal_service.service.DataService;
import rs.ac.uns.ftn.paypal_service.service.PaymentService;
import rs.ac.uns.ftn.paypal_service.service.PaypalService;



@RestController
@CrossOrigin(origins = "", allowedHeaders = "", maxAge = 3600)
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private PaypalService paypalService;
    
    @Autowired
    private DataService dataService;
    
    /**
     * A method that, based on all the payment information, creates a redirect link to PayPal.
     * @param paymentOrderRequest
     * @return
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<PaymentOrderResponse> createOrder(@RequestBody PaymentOrderRequest paymentOrderRequest){
        PaymentOrderResponse paymentOrderResponse = paymentService.createOrder(paymentOrderRequest);
        return ResponseEntity.ok().body(paymentOrderResponse);
    }
    
    /**
     * A method that confirms the payment to the seller's account by the buyer.
     * @param paymentCompleteRequest
     * @return
     */
	@PostMapping(value = "/complete")
	public ResponseEntity<Map<String, Object>> completePayment(@RequestBody PaymentCompleteRequest paymentCompleteRequest){
	    return ResponseEntity.ok().body(paypalService.completePayment(paymentCompleteRequest));
	}
	
	/**
	 * The method that returns on which port the PayPal service frontend works.
	 * @return
	 */
	@GetMapping(value = "/frontendPort")
	public ResponseEntity<Long> frontendPort(){
		long port = 4201;
	    return ResponseEntity.ok().body(port);
	}
	
	/**
	 * A method that deals with storing all the necessary information for payment from sellers
	 * @param userDataRequest
	 * @return
	 */
	@PostMapping(value = "/saveData")
	public ResponseEntity<Boolean> saveData(@RequestBody UserDataRequest userDataRequest){
	    return ResponseEntity.ok().body(dataService.saveInfo(userDataRequest));
	}
	
	/**
	 * A method that stores the details of a failed paypal transaction.
	 * @param username
	 * @return
	 */
	@PostMapping(value = "/cancelData")
	public Boolean cancelData(@RequestBody PaymentCompleteRequest paymentCompleteRequest){
	    return paypalService.cancelTransaction(paymentCompleteRequest.getToken());
	}
	
	/**
     * A method that create PayPal subscription plan..
     * @param paymentOrderRequest
     * @return
     */
	@PostMapping(value = "/createSubscriptionPlan")
	public ResponseEntity<Boolean> createSubscriptionPlan(@RequestBody SubscriptionPlanRequest subscriptionPlanRequest){
		return ResponseEntity.ok().body(paypalService.createSubscriptionPlan(subscriptionPlanRequest));
	}
	
	@PostMapping(value = "/getSubscriptionPlans")
	public ResponseEntity<List<SubscriptionPlan>> getSubscriptionPlans(@RequestBody TransactionPlanLinkRequest transactionRequest){
		return new ResponseEntity<List<SubscriptionPlan>>(paymentService.getSubscriptionPlans(transactionRequest), HttpStatus.OK);
	}
	
	@DeleteMapping("/deletePlan/{id}")
    void deleteEmployee(@PathVariable Long id) {
		paypalService.deletePlan(id);
    }
	
	@PostMapping(value = "/getTransactionPlanLink")
	public ResponseEntity<TransactionPlanLinkResponse> getTransactionLink(@RequestBody TransactionPlanLinkRequest transactionRequest) {
		return new ResponseEntity<TransactionPlanLinkResponse>(paymentService.getTransactionPlanLink(transactionRequest), HttpStatus.OK);
	}
	
	/**
     * A method that, based on all plan information, creates a redirect link to PayPal agreement.
     * @param paymentOrderRequest
     * @return
     */
	/*@RequestMapping(value = "/createAgreement", method = RequestMethod.POST)
    public ResponseEntity<PaymentOrderResponse> createAgreement(@RequestBody AgreementRequest agreementRequest) throws PayPalRESTException{
        PaymentOrderResponse paymentOrderResponse = paypalService.createAgreement(agreementRequest);
        return ResponseEntity.ok().body(paymentOrderResponse);
    }*/
	
	/**
     * A method that complete PayPal agreement.
     * @param paymentOrderRequest
     * @return
     */
	/*@PostMapping(value = "/completeAgreement")
	public ResponseEntity<CompleteAgreementResponse> completeAgreement(@RequestBody AgreementCompleteRequest agreementCompleteRequest){
	    return ResponseEntity.ok().body(paypalService.completeAgreement(agreementCompleteRequest));
	}*/
	
}
