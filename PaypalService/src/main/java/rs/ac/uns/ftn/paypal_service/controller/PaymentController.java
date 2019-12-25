package rs.ac.uns.ftn.paypal_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.paypal_service.dto.request.PaymentCompleteRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.request.UserDataRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
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
    //kreiranje url za paypal
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<PaymentOrderResponse> createOrder(@RequestBody PaymentOrderRequest paymentOrderRequest){
    	System.out.println(paymentOrderRequest.getUsername());
        PaymentOrderResponse paymentOrderResponse = paymentService.createOrder(paymentOrderRequest);
        return ResponseEntity.ok().body(paymentOrderResponse);
    }

    // TODO Delete
    @GetMapping("/paypal-test")
    public String test(){
        System.out.println("<<< In paypal >>>");
        return "In paypal";
    }
    
    /**
	 * Ovu medotu pozivamo kada se po povratku sa paypala ona potvrdjuje izvrsenu uplatu
	 * @param credencial
	 * @return
	 */
	@PostMapping(value = "/complete")
	public Map<String, Object> completePayment(@RequestBody PaymentCompleteRequest paymentCompleteRequest){
	    return paypalService.completePayment(paymentCompleteRequest);
	}
	
	//metoda koja govori na kom portu radi front od paypala
	@GetMapping(value = "/frontendPort")
	public Long frontendPort(){
		long port = 4201;
	    return port;
	}
	
	@PostMapping(value = "/saveData")
	public Boolean saveData(@RequestBody UserDataRequest userDataRequest){
	    return dataService.saveInfo(userDataRequest);
	}
	
	
}
