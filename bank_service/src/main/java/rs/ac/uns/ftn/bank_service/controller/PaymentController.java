package rs.ac.uns.ftn.bank_service.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.bank_service.dto.CardPaymentRequestDTO;
import rs.ac.uns.ftn.bank_service.dto.CardPaymentResponseDTO;
import rs.ac.uns.ftn.bank_service.dto.PaymentRegistrationDTO;
import rs.ac.uns.ftn.bank_service.dto.SimpleResponseDTO;
import rs.ac.uns.ftn.bank_service.service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${client.port}")
    private Long clientPort;

    private static Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "HI!!";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String testPOST(@RequestBody String payload){
        LOGGER.debug("DEBUGGG LOGG");
        System.out.println("IN TEST POST " + payload);
        LOGGER.info("INFOOO LOG");
        return "HI!!";
    }


    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseEntity<CardPaymentResponseDTO> createPaymentRequest(@RequestBody CardPaymentRequestDTO cardPaymentRequestDTO){
        return ResponseEntity.ok(paymentService.createPaymentRequest(cardPaymentRequestDTO));
    }

    @RequestMapping(value = "/payment-success/{transaction-id}", method = RequestMethod.PUT)
    public ResponseEntity<SimpleResponseDTO> paymentSuccess(@PathVariable("transaction-id") String transactionId){
        return ResponseEntity.ok().body(paymentService.transactionSuccess(transactionId));
    }

    @RequestMapping(value = "/payment-failed/{transaction-id}", method = RequestMethod.PUT)
    public ResponseEntity<SimpleResponseDTO> paymentFailed(@PathVariable("transaction-id") String transactionId){
        return ResponseEntity.ok().body(paymentService.transactionFailed(transactionId));
    }

    @RequestMapping(value = "/payment-error/{transaction-id}", method = RequestMethod.PUT)
    public ResponseEntity<SimpleResponseDTO> paymentError(@PathVariable("transaction-id") String transactionId){
        return ResponseEntity.ok().body(paymentService.transactionError(transactionId));
    }
    
    @RequestMapping(value = "/payment-registration", method = RequestMethod.POST)
    public ResponseEntity<SimpleResponseDTO> paymentRegistration(@RequestBody PaymentRegistrationDTO paymentRegistrationDTO){
        return ResponseEntity.ok().body(paymentService.paymentRegistration(paymentRegistrationDTO));
    }

    @RequestMapping(value = "/frontendPort", method = RequestMethod.GET)
    public Long getClientPort(){
        return clientPort;
    }

}
