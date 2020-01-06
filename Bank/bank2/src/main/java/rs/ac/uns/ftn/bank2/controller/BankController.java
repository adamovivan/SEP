package rs.ac.uns.ftn.bank2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bank.dto.*;
import rs.ac.uns.ftn.bank.service.PaymentRequestService;
import rs.ac.uns.ftn.bank.service.PaymentService;

import javax.validation.Valid;

@CrossOrigin(origins = "/*")
@RestController
public class BankController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRequestService paymentRequestService;

    @RequestMapping(value="/create-payment-request", method = RequestMethod.POST)
    public ResponseEntity<PaymentResponseDTO> createPaymentRequest(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO){
        return ResponseEntity.ok(paymentRequestService.createPaymentRequest(paymentRequestDTO));
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<PaymentStatusDTO> pay(@RequestBody PaymentCardDTO paymentCardDTO){
        return ResponseEntity.ok(paymentService.pay(paymentCardDTO));
    }

    @RequestMapping(value = "/get-callback-urls/{payment-id}")
    public ResponseEntity<CallbackUrlsDTO> getCallbackUrls(@PathVariable("payment-id") String paymentId){
        return ResponseEntity.ok(paymentService.getCallbackUrls(paymentId));
    }
}
