package rs.ac.uns.ftn.bank_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.bank_service.dto.CardPaymentRequestDTO;
import rs.ac.uns.ftn.bank_service.dto.CardPaymentResponseDTO;
import rs.ac.uns.ftn.bank_service.dto.PaymentRequestDTO;
import rs.ac.uns.ftn.bank_service.exception.ExceptionResolver;
import rs.ac.uns.ftn.bank_service.exception.NotFoundException;
import rs.ac.uns.ftn.bank_service.model.Merchant;
import rs.ac.uns.ftn.bank_service.repository.MerchantRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bank.url}")
    private String bankUrl;

    @Value("${bank.create.payment.request.api}")
    private String createPaymentRequestApi;

    public CardPaymentResponseDTO createPaymentRequest(CardPaymentRequestDTO cardPaymentRequestDTO){
        Merchant merchant = merchantRepository.findByUsername(cardPaymentRequestDTO.getMerchantUsername());

        if(merchant == null){
            throw new NotFoundException("Merchant doesn't exist.");
        }

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setMerchantId(merchant.getMerchantId());
        paymentRequestDTO.setMerchantPassword(merchant.getMerchantPassword());
        paymentRequestDTO.setAmount(cardPaymentRequestDTO.getAmount());
        paymentRequestDTO.setMerchantOrderId(UUID.randomUUID().toString());
        paymentRequestDTO.setMerchantTimestamp(LocalDateTime.now());
        paymentRequestDTO.setSuccessUrl("successURL");
        paymentRequestDTO.setFailedUrl("failedURL");
        paymentRequestDTO.setErrorUrl("errorURL");
        logger.info("Payment request is succesfully created for user " + cardPaymentRequestDTO.getMerchantUsername() + ".");
        return restTemplate.postForObject(bankUrl+createPaymentRequestApi, paymentRequestDTO, CardPaymentResponseDTO.class);
    }
}
