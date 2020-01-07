package rs.ac.uns.ftn.pcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.pcc.dto.AcquirerTransactionRequestDTO;
import rs.ac.uns.ftn.pcc.dto.PaymentStatusDTO;
import rs.ac.uns.ftn.pcc.service.PccService;

@RestController
public class PccController {

    @Autowired
    private PccService pccService;

    @RequestMapping(value = "/transaction-request", method = RequestMethod.POST)
    public ResponseEntity<PaymentStatusDTO> forwardTransactionRequest(@RequestBody AcquirerTransactionRequestDTO acquirerTransactionRequestDTO){
        return ResponseEntity.ok().body(pccService.forwardTransactionRequest(acquirerTransactionRequestDTO));
    }
}
