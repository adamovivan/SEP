package rs.ac.uns.ftn.payment_concentrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.payment_concentrator.dto.OrderDTO;
import rs.ac.uns.ftn.payment_concentrator.dto.request.PaymentOrderRequest;
import rs.ac.uns.ftn.payment_concentrator.dto.response.PaymentOrderResponse;
import rs.ac.uns.ftn.payment_concentrator.model.Payment;
import rs.ac.uns.ftn.payment_concentrator.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${zuul-gateway-url}")
    private String zuulUrl;

    public PaymentOrderResponse createOrder(PaymentOrderRequest paymentOrderRequest){
        if(paymentOrderRequest.getMagazineId() == null){
            throw new NullPointerException();
        }
        Payment payment = paymentRepository.findByClientMagazineId(paymentOrderRequest.getMagazineId());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalPrice(paymentOrderRequest.getTotalPrice());
        orderDTO.setIntent("sale");
        orderDTO.setClientId(payment.getPaymentId());
        orderDTO.setClientSecret(payment.getPaymentSecret());
        orderDTO.setSuccessUrl(payment.getSuccessUrl());
        orderDTO.setCancelUrl(payment.getCancelUrl());

        String responseUrl = restTemplate.postForObject(zuulUrl + "/paypal-api/createPayment", orderDTO, String.class);
        System.out.println(responseUrl);
        return new PaymentOrderResponse(true, responseUrl);
    }
}
