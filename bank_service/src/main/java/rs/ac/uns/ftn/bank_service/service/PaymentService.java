package rs.ac.uns.ftn.bank_service.service;


import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.ac.uns.ftn.bank_service.dto.CardPaymentRequestDTO;
import rs.ac.uns.ftn.bank_service.dto.CardPaymentResponseDTO;
import rs.ac.uns.ftn.bank_service.dto.PaymentRegistrationDTO;
import rs.ac.uns.ftn.bank_service.dto.PaymentRequestDTO;
import rs.ac.uns.ftn.bank_service.dto.SimpleResponseDTO;
import rs.ac.uns.ftn.bank_service.exception.ExceptionResolver;
import rs.ac.uns.ftn.bank_service.exception.NotFoundException;
import rs.ac.uns.ftn.bank_service.model.Merchant;
import rs.ac.uns.ftn.bank_service.model.Transaction;
import rs.ac.uns.ftn.bank_service.model.TransactionStatus;
import rs.ac.uns.ftn.bank_service.repository.MerchantRepository;
import rs.ac.uns.ftn.bank_service.repository.TransactionRepository;

@Service
public class PaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bank.url}")
    private String bankUrl;

    @Value("${bank.create.payment.request.api}")
    private String createPaymentRequestApi;

    @Value("${bank.service.success.url}")
    private String successUrl;

    @Value("${bank.service.failed.url}")
    private String failedUrl;

    @Value("${bank.service.error.url}")
    private String errorUrl;

    public CardPaymentResponseDTO createPaymentRequest(CardPaymentRequestDTO cardPaymentRequestDTO){
        Merchant merchant = merchantRepository.findByUsername(cardPaymentRequestDTO.getUsername());

        if(merchant == null){
            throw new NotFoundException("Merchant doesn't exist.");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setMerchantId(merchant.getMerchantId());
        transaction.setMerchantPassword(merchant.getMerchantPassword());
        transaction.setAmount(cardPaymentRequestDTO.getTotalPrice());
        //transaction.setMerchantOrderId(cardPaymentRequestDTO.getMerchantOrderId());
        transaction.setMerchantOrderId(UUID.randomUUID().toString());
        transaction.setMerchantTimestamp(LocalDateTime.now());
        transaction.setTransactionStatus(TransactionStatus.CREATED);

        Transaction savedTransaction = transactionRepository.save(transaction);
        PaymentRequestDTO paymentRequestDTO = transactionToPaymentRequestDTO(savedTransaction);

        logger.info("Payment request is successfully created for user " + cardPaymentRequestDTO.getUsername() + ".");

        return restTemplate.postForObject(bankUrl+createPaymentRequestApi, paymentRequestDTO, CardPaymentResponseDTO.class);
    }

    private PaymentRequestDTO transactionToPaymentRequestDTO(Transaction transaction){
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setMerchantId(transaction.getMerchantId());
        paymentRequestDTO.setMerchantPassword(transaction.getMerchantPassword());
        paymentRequestDTO.setAmount(transaction.getAmount());
        paymentRequestDTO.setMerchantOrderId(transaction.getMerchantOrderId());
        paymentRequestDTO.setMerchantTimestamp(LocalDateTime.now());
        paymentRequestDTO.setSuccessUrl(successUrl + "/" + transaction.getTransactionId());
        paymentRequestDTO.setFailedUrl(failedUrl + "/" + transaction.getTransactionId());
        paymentRequestDTO.setErrorUrl(errorUrl + "/" + transaction.getTransactionId());
        return paymentRequestDTO;
    }

    public SimpleResponseDTO transactionSuccess(String transactionId){
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if(transaction == null){
            throw new NotFoundException("Transaction doesn't exist.");
        }
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transactionRepository.save(transaction);
        return new SimpleResponseDTO("Success.");
    }

    public SimpleResponseDTO transactionFailed(String transactionId){
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if(transaction == null){
            throw new NotFoundException("Transaction doesn't exist.");
        }
        transaction.setTransactionStatus(TransactionStatus.FAILED);
        transactionRepository.save(transaction);
        return new SimpleResponseDTO("Success.");
    }

    public SimpleResponseDTO transactionError(String transactionId){
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if(transaction == null){
            throw new NotFoundException("Transaction doesn't exist.");
        }
        transaction.setTransactionStatus(TransactionStatus.ERROR);
        transactionRepository.save(transaction);
        return new SimpleResponseDTO("Success.");
    }
    
    public SimpleResponseDTO paymentRegistration(PaymentRegistrationDTO paymentRegistationDTO) {
    	Merchant merchant = new Merchant();
    	merchant.setUsername(paymentRegistationDTO.getUsername());
    	merchant.setMerchantId(paymentRegistationDTO.getMerchantId());
    	merchant.setMerchantPassword(paymentRegistationDTO.getMerchantPassword());
    	merchantRepository.save(merchant);
    	return new SimpleResponseDTO("Success.");
    }
}
