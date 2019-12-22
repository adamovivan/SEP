package rs.ac.uns.ftn.paypal_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.paypal_service.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.paypal_service.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.paypal_service.service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<PaymentOrderResponse> createOrder(@RequestBody PaymentOrderRequest paymentOrderRequest){
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
	 * Ovu medotu pozivamo kada se po povratku sa paypala na osnovu zadatih linkova pogodi http://localhost:4200/success ... gore opisano ...
	 * @param credencial
	 * @return
	 
	@PostMapping(value = "/completePayment")
	public Map<String, Object> completePayment(@RequestBody Credencial credencial){
	    return service.completePayment(credencial.getPaymentID(),credencial.getPayerID());
	}*/
	
}
