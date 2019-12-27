package rs.ac.uns.ftn.bank_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.bank_service.dto.CardPaymentRequestDTO;
import rs.ac.uns.ftn.bank_service.dto.CardPaymentResponseDTO;
import rs.ac.uns.ftn.bank_service.service.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${client.port}")
    private Long clientPort;

    @RequestMapping(value = "/create-payment-request", method = RequestMethod.POST)
    public ResponseEntity<CardPaymentResponseDTO> createOrder(@RequestBody CardPaymentRequestDTO cardPaymentRequestDTO){
        return ResponseEntity.ok(paymentService.createPaymentRequest(cardPaymentRequestDTO));
    }

    @RequestMapping(value = "/frontendPort", method = RequestMethod.GET)
    public Long getClientPort(){
        return clientPort;
    }

}
