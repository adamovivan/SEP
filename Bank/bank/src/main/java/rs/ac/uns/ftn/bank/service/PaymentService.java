package rs.ac.uns.ftn.bank.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bank.dto.PaymentCardDTO;
import rs.ac.uns.ftn.bank.dto.PaymentStatusDTO;
import rs.ac.uns.ftn.bank.exception.BadRequestException;
import rs.ac.uns.ftn.bank.exception.NotFoundException;
import rs.ac.uns.ftn.bank.model.*;
import rs.ac.uns.ftn.bank.repository.CardRepository;
import rs.ac.uns.ftn.bank.repository.PaymentRequestRepository;
import rs.ac.uns.ftn.bank.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PaymentService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentRequestService.class);

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private PaymentRequestRepository paymentRequestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Value("${bank.iin}")
    private String bankIIN;

    public PaymentStatusDTO pay(PaymentCardDTO paymentCardDTO){
        validate(paymentCardDTO);

        if(!isSameBank(paymentCardDTO.getPan())){
            return null;
            // TODO pcc
        }

        // same bank
        if(!checkBalance(paymentCardDTO.getPan(), paymentCardDTO.getPaymentId())){
            throw new BadRequestException("You don't have enough money for this transaction.");
        }

        Reservation reservation = reserveMoney(paymentCardDTO.getPan(), paymentCardDTO.getPaymentId());
        logger.info("Payment Transaction is successfully completed to user " + paymentCardDTO.getCardholderName() + " account.");
        return new PaymentStatusDTO(reservation.getPaymentId(), PaymentStatus.SUCCESS);
    }

    private void validate(PaymentCardDTO paymentCardDTO){
        if(!paymentRequestExist(paymentCardDTO.getPaymentId())){
            throw new BadRequestException("Unknown payment id.");
        }
        if(isPaymentRequestExpired(paymentCardDTO.getPaymentId())){
            throw new BadRequestException("Payment request expired.");
        }
        if(isPaymentIdUsed(paymentCardDTO.getPaymentId())){
            throw new BadRequestException("Payment id is already used.");
        }
        if(!isValidPAN(paymentCardDTO.getPan())){
            throw new BadRequestException("Invalid PAN.");
        }
        if(!isValidCardhoderName(paymentCardDTO.getCardholderName())){
            throw new BadRequestException("Invalid CardholderName.");
        }
        if(!isValidCVV(paymentCardDTO.getCvv())){
            throw new BadRequestException("Invalid CVV.");
        }
        if(!isValidExpiryDate(paymentCardDTO.getExpiryDate())){
            throw new BadRequestException("Invalid expiry date.");
        }
        if(isCardExpired(paymentCardDTO.getExpiryDate())){
            throw new BadRequestException("Card expired.");
        }
    }

    private Reservation reserveMoney(String pan, String paymentId){
        Card card = cardRepository.findByPan(pan);
        PaymentRequest paymentRequest = paymentRequestRepository.findByPaymentId(paymentId);
        paymentRequest.setUsed(true);
        paymentRequestRepository.save(paymentRequest);
        BankAccount bankAccount = card.getBankAccount();
        bankAccount.setBalance(bankAccount.getBalance() - paymentRequest.getAmount());

        Reservation reservation = new Reservation();
        reservation.setPaymentId(paymentId);
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

}
