package rs.ac.uns.ftn.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.bank.dto.*;
import rs.ac.uns.ftn.bank.exception.BadRequestException;
import rs.ac.uns.ftn.bank.exception.NotFoundException;
import rs.ac.uns.ftn.bank.model.*;
import rs.ac.uns.ftn.bank.repository.CardRepository;
import rs.ac.uns.ftn.bank.repository.PaymentRequestRepository;
import rs.ac.uns.ftn.bank.repository.ReservationRepository;
import rs.ac.uns.ftn.bank.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PaymentService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${bank.iin}")
    private String bankIIN;

    @Value("${pcc.url}")
    private String pccUrl;

    public PaymentStatusDTO pay(PaymentCardDTO paymentCardDTO){
        validate(paymentCardDTO);

        if(!isSameBank(paymentCardDTO.getPan())){
            return forwardToPcc(paymentCardDTO);
        }

        // same bank
        validateCard(paymentCardDTO);

        if(!checkBalance(paymentCardDTO.getPan(), paymentCardDTO.getPaymentId())){
            throw new BadRequestException("You don't have enough money for this transaction.");
        }

        Reservation reservation = reserveMoney(paymentCardDTO.getPan(), paymentCardDTO.getPaymentId());

        return new PaymentStatusDTO(reservation.getTransaction().getTransactionId(), reservation.getTransaction().getTransactionStatus());
    }

    private void validate(PaymentCardDTO paymentCardDTO){
        if(!paymentRequestExist(paymentCardDTO.getPaymentId())){
            throw new NotFoundException("Unknown payment id.");
        }
        if(isPaymentRequestExpired(paymentCardDTO.getPaymentId())){
            throw new BadRequestException("Payment request expired.");
        }
        if(isPaymentIdUsed(paymentCardDTO.getPaymentId())){
            throw new BadRequestException("Payment id is already used.");
        }
    }

    private void validateCard(PaymentCardDTO paymentCardDTO){
        if(!isValidPAN(paymentCardDTO.getPan())){
            throw new BadRequestException("Invalid data.");
        }
        if(!isValidCardhoderName(paymentCardDTO.getCardholderName())){
            throw new BadRequestException("Invalid data.");
        }
        if(!isValidCVV(paymentCardDTO.getCvv())){
            throw new BadRequestException("Invalid data.");
        }
        if(!isValidExpiryDate(paymentCardDTO.getExpiryDate())){
            throw new BadRequestException("Invalid data.");
        }
        if(isCardExpired(paymentCardDTO.getExpiryDate())){
            throw new BadRequestException("Card expired.");
        }
    }

    private PaymentStatusDTO forwardToPcc(PaymentCardDTO paymentCardDTO){
        PaymentRequest paymentRequest = paymentRequestRepository.findByPaymentId(paymentCardDTO.getPaymentId());
        AcquirerTransactionRequestDTO acquirerTransactionRequestDTO = new AcquirerTransactionRequestDTO();
        acquirerTransactionRequestDTO.setAcquirerInn(bankIIN);
        acquirerTransactionRequestDTO.setAcquirerOrderId(UUID.randomUUID().toString());
        acquirerTransactionRequestDTO.setAcquirerTimestamp(LocalDateTime.now());
        acquirerTransactionRequestDTO.setPan(paymentCardDTO.getPan());
        acquirerTransactionRequestDTO.setCardholderName(paymentCardDTO.getCardholderName());
        acquirerTransactionRequestDTO.setCvv(paymentCardDTO.getCvv());
        acquirerTransactionRequestDTO.setExpiryDate(paymentCardDTO.getExpiryDate());
        acquirerTransactionRequestDTO.setAmount(paymentRequest.getAmount());

        Transaction transaction = transactionRepository.findByTransactionId(paymentCardDTO.getPaymentId());
        if(transaction == null){
            throw new NotFoundException("Transaction with provided id doesn't exist.");
        }

        PaymentStatusPccDTO paymentStatusPccDTO = restTemplate.postForObject(pccUrl, acquirerTransactionRequestDTO, PaymentStatusPccDTO.class);
        if(paymentStatusPccDTO == null){
            throw new NullPointerException("PCC returned null.");
        }

        transaction.setTransactionStatus(paymentStatusPccDTO.getTransactionStatus());
        transactionRepository.save(transaction);

        return new PaymentStatusDTO(paymentCardDTO.getPaymentId(), paymentStatusPccDTO.getTransactionStatus());
    }

    private Reservation reserveMoney(String pan, String paymentId){
        Card card = cardRepository.findByPan(pan);
        PaymentRequest paymentRequest = paymentRequestRepository.findByPaymentId(paymentId);
        paymentRequest.setUsed(true);
        paymentRequestRepository.save(paymentRequest);
        BankAccount bankAccount = card.getBankAccount();
        bankAccount.setBalance(bankAccount.getBalance() - paymentRequest.getAmount());

        Transaction transaction = transactionRepository.findByTransactionId(paymentId);
        if(transaction == null){
            throw new NotFoundException("Transaction with provided id doesn't exist.");
        }
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        Reservation reservation = new Reservation();
        reservation.setTransaction(transaction);
        reservation.setBankAccount(bankAccount);
        reservation.setAmount(paymentRequest.getAmount());
        bankAccount.getReservations().add(reservation);
        return reservationRepository.save(reservation);
    }

    private Boolean isValidPAN(String pan){
        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
                "(?<mastercard>5[1-5][0-9]{14})|" +
                "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                "(?<amex>3[47][0-9]{13})|" +
                "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pan);
        return matcher.matches();
    }

    private Boolean isValidCVV(String cvv){
        return cardRepository.findByCvv(cvv) != null;
    }

    private Boolean isValidExpiryDate(LocalDate expiryDate){
        return cardRepository.findByExpiryDate(expiryDate) != null;
    }

    private Boolean isValidCardhoderName(String cardholderName){
        return cardRepository.findByCardholderName(cardholderName) != null;
    }

    private Boolean isCardExpired(LocalDate expiryDate){
        return expiryDate.isBefore(LocalDate.now());
    }

    private Boolean isPaymentIdUsed(String paymentId){
        return paymentRequestRepository.findByPaymentId(paymentId).getUsed();
    }

    private Boolean isPaymentRequestExpired(String paymentId){
        PaymentRequest paymentRequest = paymentRequestRepository.findByPaymentId(paymentId);
        return paymentRequest.getExpiryTime().isBefore(LocalDateTime.now());
    }

    private Boolean paymentRequestExist(String paymentId){
        return paymentRequestRepository.findByPaymentId(paymentId) != null;
    }

    private Boolean checkBalance(String pan, String paymentId){
        Card card = cardRepository.findByPan(pan);
        if(card == null){
            throw new NotFoundException("Card doesn't exist.");
        }
        PaymentRequest paymentRequest = paymentRequestRepository.findByPaymentId(paymentId);
        if(paymentRequest == null){
            throw new NotFoundException("Payment id doesn't exist.");
        }
        return card.getBankAccount().getBalance() > paymentRequest.getAmount();
    }

    private Boolean isSameBank(String pan){
        return pan.startsWith(bankIIN);
    }

    public CallbackUrlsDTO getCallbackUrls(String paymentId){
        PaymentRequest paymentRequest = paymentRequestRepository.findByPaymentId(paymentId);

        if(paymentRequest == null){
            throw new NotFoundException("Payment id not found.");
        }

        CallbackUrlsDTO callbackUrlsDTO = new CallbackUrlsDTO();
        callbackUrlsDTO.setSuccessUrl(paymentRequest.getSuccessUrl());
        callbackUrlsDTO.setFailedUrl(paymentRequest.getFailedUrl());
        callbackUrlsDTO.setErrorUrl(paymentRequest.getErrorUrl());
        return callbackUrlsDTO;
    }
}
