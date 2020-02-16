package rs.ac.uns.ftn.bank_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.bank_service.dto.*;
import rs.ac.uns.ftn.bank_service.exception.NotFoundException;
import rs.ac.uns.ftn.bank_service.model.Merchant;
import rs.ac.uns.ftn.bank_service.model.NotificationStatus;
import rs.ac.uns.ftn.bank_service.model.Transaction;
import rs.ac.uns.ftn.bank_service.model.TransactionStatus;
import rs.ac.uns.ftn.bank_service.repository.MerchantRepository;
import rs.ac.uns.ftn.bank_service.repository.TransactionRepository;
import rs.ac.uns.ftn.bank_service.util.EncryptDecrypt;

@Service
public class PaymentService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bank.url}")
    private String bankUrl;

    @Value("${zuul.gateway.url}")
    private String zuulGateway;

    @Value("${bank.create.payment.request.api}")
    private String createPaymentRequestApi;

    @Value("${bank.get.transaction.api}")
    private String getTransactionStatusApi;

    @Value("${bank.service.success.url}")
    private String successUrl;

    @Value("${bank.service.failed.url}")
    private String failedUrl;

    @Value("${bank.service.error.url}")
    private String errorUrl;

    @Value("${transaction.expiration.interval}")
    private Integer transactionExpirationInterval;

    public CardPaymentResponseDTO createPaymentRequest(CardPaymentRequestDTO cardPaymentRequestDTO){
        Merchant merchant = merchantRepository.findByUsername(cardPaymentRequestDTO.getUsername());

        if(merchant == null){
            throw new NotFoundException("Merchant doesn't exist.");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setMerchantId(EncryptDecrypt.decrypt(merchant.getMerchantId()));
        transaction.setMerchantPassword(EncryptDecrypt.decrypt(merchant.getMerchantPassword()));
        transaction.setAmount(cardPaymentRequestDTO.getTotalPrice());
        transaction.setMerchantOrderId(cardPaymentRequestDTO.getOrderId());
        transaction.setMerchantTimestamp(LocalDateTime.now());
        transaction.setCallbackUrl(cardPaymentRequestDTO.getCallbackUrl());
        transaction.setTransactionStatus(TransactionStatus.CREATED);
        transaction.setNotificationStatus(NotificationStatus.NOT_NOTIFIED);

        Transaction savedTransaction = transactionRepository.save(transaction);
        PaymentRequestDTO paymentRequestDTO = transactionToPaymentRequestDTO(savedTransaction);

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

    public SimpleResponseDTO completePayment(String transactionId, TransactionStatus transactionStatus){
        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        if(transaction == null){
            throw new NotFoundException("Transaction doesn't exist.");
        }
        transaction.setTransactionStatus(transactionStatus);
        transactionRepository.save(transaction);

        CompletePaymentDTO completePaymentDTO = new CompletePaymentDTO(transaction.getMerchantOrderId(), transaction.getTransactionStatus());

        if(notify(transaction)){
            return new SimpleResponseDTO("Success.");
        }
        SimpleResponseDTO simpleResponseDTO = restTemplate.postForObject(transaction.getCallbackUrl(), completePaymentDTO, SimpleResponseDTO.class);

        if(simpleResponseDTO != null && simpleResponseDTO.getSuccess()){
            transaction.setNotificationStatus(NotificationStatus.NOTIFIED);
            transactionRepository.save(transaction);
            return new SimpleResponseDTO("Success.");
        }

        return new SimpleResponseDTO(false,"Failed.");
    }

    private Boolean notify(Transaction transaction){
        CompletePaymentDTO completePaymentDTO = new CompletePaymentDTO(transaction.getMerchantOrderId(), transaction.getTransactionStatus());
        SimpleResponseDTO simpleResponseDTO = restTemplate.postForObject(transaction.getCallbackUrl(), completePaymentDTO, SimpleResponseDTO.class);

        if(simpleResponseDTO != null && simpleResponseDTO.getSuccess()){
            transaction.setNotificationStatus(NotificationStatus.NOTIFIED);
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }
    
    public SimpleResponseDTO paymentRegistration(PaymentRegistrationDTO paymentRegistrationDTO) {
    	Merchant merchant = new Merchant();
    	merchant.setUsername(paymentRegistrationDTO.getUsername());
    	merchant.setMerchantId(EncryptDecrypt.encrypt(paymentRegistrationDTO.getMerchantId()));
    	merchant.setMerchantPassword(EncryptDecrypt.encrypt(paymentRegistrationDTO.getMerchantPassword()));
    	merchantRepository.save(merchant);
    	return new SimpleResponseDTO("Success.");
    }

    public void unfinishedTransactionsCheck(){
        System.out.println("CHECKING: " + LocalDateTime.now());
        List<Transaction> transactions = transactionRepository.findByTransactionStatus(TransactionStatus.CREATED);

        for(Transaction transaction: transactions){
            TransactionStatusDTO transactionStatusDTO = restTemplate.getForObject(bankUrl+getTransactionStatusApi+transaction.getMerchantOrderId(), TransactionStatusDTO.class);

            if(transactionStatusDTO == null){
                continue;
            }

            LocalDateTime transactionTimestamp = transaction.getMerchantTimestamp();
            //if(transactionTimestamp.plusSeconds(transactionExpirationInterval).isBefore(LocalDateTime.now())){
                transaction.setTransactionStatus(transactionStatusDTO.getTransactionStatus());
           // }
        }
        transactionRepository.saveAll(transactions);

        transactions = transactionRepository.findByNotificationStatus(NotificationStatus.NOT_NOTIFIED);

        transactions.forEach(transaction -> {
                    LocalDateTime transactionTimestamp = transaction.getMerchantTimestamp();
                    if (transactionTimestamp.plusSeconds(transactionExpirationInterval).isBefore(LocalDateTime.now())) {
                        notify(transaction);
                    }
         });
    }

}
