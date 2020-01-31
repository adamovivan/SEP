package rs.ac.uns.ftn.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bank.dto.*;
import rs.ac.uns.ftn.bank.model.TransactionStatus;
import rs.ac.uns.ftn.bank.service.PaymentService;
import rs.ac.uns.ftn.bank.service.PaymentRequestService;

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

    @RequestMapping(value = "/get-transaction-status/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<TransactionStatusDTO> getTransactionStatus(@PathVariable String orderId) throws InterruptedException {
        return ResponseEntity.ok().body(paymentService.getTransactionStatus(orderId));
    }

    @RequestMapping(value = "/start-utc", method = RequestMethod.GET)
    public ResponseEntity startUTC() throws InterruptedException {
        paymentService.startUTC();
        return null;
    }

    @RequestMapping(value = "/stop-utc", method = RequestMethod.GET)
    public ResponseEntity stopUTC() {
        paymentService.stopUTC();
        return null;
    }

    @RequestMapping(value = "/utc-timeout/{timeout}", method = RequestMethod.GET)
    public ResponseEntity setTimeoutUtc(@PathVariable Integer timeout) {
        paymentService.setTimeoutUtc(timeout);
        return null;
    }
}
