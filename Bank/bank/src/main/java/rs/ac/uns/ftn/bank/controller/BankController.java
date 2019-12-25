package rs.ac.uns.ftn.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bank.dto.PaymentCardDTO;
import rs.ac.uns.ftn.bank.dto.PaymentRequestDTO;
import rs.ac.uns.ftn.bank.dto.PaymentResponseDTO;
import rs.ac.uns.ftn.bank.dto.PaymentStatusDTO;
import rs.ac.uns.ftn.bank.service.PaymentService;
import rs.ac.uns.ftn.bank.service.PaymentRequestService;

import javax.validation.Valid;


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
}
