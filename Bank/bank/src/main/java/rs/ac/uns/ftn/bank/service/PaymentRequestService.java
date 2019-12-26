package rs.ac.uns.ftn.bank.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bank.dto.PaymentRequestDTO;
import rs.ac.uns.ftn.bank.dto.PaymentResponseDTO;
import rs.ac.uns.ftn.bank.exception.BadRequestException;
import rs.ac.uns.ftn.bank.mapper.PaymentRequestMapper;
import rs.ac.uns.ftn.bank.model.Merchant;
import rs.ac.uns.ftn.bank.model.PaymentRequest;
import rs.ac.uns.ftn.bank.repository.MerchantRepository;
import rs.ac.uns.ftn.bank.repository.PaymentRequestRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentRequestService {

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private PaymentRequestMapper paymentRequestMapper;

    @Value("${bank.payment.url}")
    private String bankPaymentUrl;

    public PaymentResponseDTO createPaymentRequest(PaymentRequestDTO paymentRequestDTO){

        if(!isValidMerchant(paymentRequestDTO)){
            throw new BadRequestException("Wrong merchant id or password.");
        }

        String paymentId = generatePaymentId();
        String paymentUrl = getPaymentUrl(paymentId);


        PaymentRequest paymentRequest = paymentRequestMapper.paymentRequestDTOtoPaymentRequest(paymentRequestDTO);
        paymentRequest.setPaymentUrl(paymentUrl);
        paymentRequest.setPaymentId(paymentId);
        paymentRequest.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        paymentRequest.setUsed(false);
        paymentRequestRepository.save(paymentRequest);

        return new PaymentResponseDTO(paymentId, paymentUrl);
    }

    private Boolean isValidMerchant(PaymentRequestDTO paymentRequestDTO){
        Merchant merchant = merchantRepository.findByMerchantIdAndMerchantPassword(paymentRequestDTO.getMerchantId(),
                paymentRequestDTO.getMerchantPassword());
        return merchant != null;
    }

    private String getPaymentUrl(String paymentId){
        return bankPaymentUrl + "/" + paymentId;
    }

    private String generatePaymentId(){
        return UUID.randomUUID().toString();
    }


}
