package rs.ac.uns.ftn.bank2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.bank2.dto.AcquirerTransactionRequestDTO;
import rs.ac.uns.ftn.bank2.dto.PaymentStatusDTO;
import rs.ac.uns.ftn.bank2.exception.BadRequestException;
import rs.ac.uns.ftn.bank2.exception.NotFoundException;
import rs.ac.uns.ftn.bank2.model.*;
import rs.ac.uns.ftn.bank2.repository.CardRepository;
import rs.ac.uns.ftn.bank2.repository.ReservationRepository;
import rs.ac.uns.ftn.bank2.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BankService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${bank.iin}")
    private String bankIIN;

    public PaymentStatusDTO pay(AcquirerTransactionRequestDTO acquirerTransactionRequestDTO){
        validate(acquirerTransactionRequestDTO);

        Transaction transaction = createTransaction(acquirerTransactionRequestDTO);

        if(!checkBalance(acquirerTransactionRequestDTO.getPan(), acquirerTransactionRequestDTO.getAmount())){
            throw new BadRequestException("You don't have enough money for this transaction.");
        }

        Reservation reservation = reserveMoney(acquirerTransactionRequestDTO.getPan(), transaction);

        return new PaymentStatusDTO(reservation.getTransaction().getAcquirerOrderId(),
                reservation.getTransaction().getAcquirerTimestamp(),
                reservation.getTransaction().getIssuerOrderId(),
                reservation.getTransaction().getIssuerTimestamp(),
                reservation.getTransaction().getTransactionStatus());
    }

    private void validate(AcquirerTransactionRequestDTO acquirerTransactionRequestDTO){
        if(isAcquirerOrderUsed(acquirerTransactionRequestDTO.getAcquirerOrderId())){
            throw new BadRequestException("Acquirer order is already used.");
        }
        if(!isValidPAN(acquirerTransactionRequestDTO.getPan())){
            throw new BadRequestException("Invalid data.");
        }
        if(!isValidCardhoderName(acquirerTransactionRequestDTO.getCardholderName())){
            throw new BadRequestException("Invalid data.");
        }
        if(!isValidCVV(acquirerTransactionRequestDTO.getCvv())){
            throw new BadRequestException("Invalid data.");
        }
        if(!isValidExpiryDate(acquirerTransactionRequestDTO.getExpiryDate())){
            throw new BadRequestException("Invalid data.");
        }
        if(isCardExpired(acquirerTransactionRequestDTO.getExpiryDate())){
            throw new BadRequestException("Card expired.");
        }
    }

    private Transaction createTransaction(AcquirerTransactionRequestDTO acquirerTransactionRequestDTO){
        Transaction transaction = new Transaction();
        transaction.setAcquirerInn(acquirerTransactionRequestDTO.getAcquirerInn());
        transaction.setAcquirerOrderId(acquirerTransactionRequestDTO.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(acquirerTransactionRequestDTO.getAcquirerTimestamp());
        transaction.setIssuerOrderId(UUID.randomUUID().toString());
        transaction.setIssuerTimestamp(LocalDateTime.now());
        transaction.setAmount(acquirerTransactionRequestDTO.getAmount());
        transaction.setTransactionStatus(TransactionStatus.OPEN);
        return transactionRepository.save(transaction);
    }

    private Reservation reserveMoney(String pan, Transaction transaction){
        Card card = cardRepository.findByPan(pan);

        BankAccount bankAccount = card.getBankAccount();
        bankAccount.setBalance(bankAccount.getBalance() - transaction.getAmount());

        Reservation reservation = new Reservation();
        reservation.setTransaction(transaction);
        reservation.setBankAccount(bankAccount);
        reservation.setAmount(transaction.getAmount());
        bankAccount.getReservations().add(reservation);
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

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

    private Boolean isAcquirerOrderUsed(String acquirerOrderId){
        Transaction transaction = transactionRepository.findByAcquirerOrderId(acquirerOrderId);
        if(transaction == null){
            return false;
        }
        return transaction.getTransactionStatus() != TransactionStatus.OPEN;
    }

    private Boolean checkBalance(String pan, Double amount){
        Card card = cardRepository.findByPan(pan);
        if(card == null){
            throw new NotFoundException("Card doesn't exist.");
        }
        return card.getBankAccount().getBalance() > amount;
    }
}
