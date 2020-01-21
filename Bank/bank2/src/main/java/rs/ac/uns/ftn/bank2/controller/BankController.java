package rs.ac.uns.ftn.bank2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bank2.dto.AcquirerTransactionRequestDTO;
import rs.ac.uns.ftn.bank2.dto.PaymentStatusDTO;
import rs.ac.uns.ftn.bank2.service.BankService;

@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    @RequestMapping(value="/transaction-request", method = RequestMethod.POST)
    public ResponseEntity<PaymentStatusDTO> createPaymentRequest(@RequestBody AcquirerTransactionRequestDTO acquirerTransactionRequestDTO){
        return ResponseEntity.ok(bankService.pay(acquirerTransactionRequestDTO));
    }

}
