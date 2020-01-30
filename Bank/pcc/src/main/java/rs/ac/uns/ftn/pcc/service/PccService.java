package rs.ac.uns.ftn.pcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.pcc.dto.AcquirerTransactionRequestDTO;
import rs.ac.uns.ftn.pcc.dto.PaymentStatusDTO;
import rs.ac.uns.ftn.pcc.exception.BadRequestException;
import rs.ac.uns.ftn.pcc.exception.InvalidDataException;
import rs.ac.uns.ftn.pcc.exception.NotFoundException;
import rs.ac.uns.ftn.pcc.model.Bank;
import rs.ac.uns.ftn.pcc.model.Transaction;
import rs.ac.uns.ftn.pcc.model.TransactionStatus;
import rs.ac.uns.ftn.pcc.repository.BankRepository;
import rs.ac.uns.ftn.pcc.repository.TransactionRepository;

@Service
public class PccService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public PaymentStatusDTO forwardTransactionRequest(AcquirerTransactionRequestDTO acquirerTransactionRequestDTO){
        Bank acquirer = bankRepository.findByInn(acquirerTransactionRequestDTO.getAcquirerInn());
        if(acquirer == null){
            throw new NotFoundException("Acquirer doesn't exist.");
        }

        Bank issuer = bankRepository.findByInn(getInnFromPan(acquirerTransactionRequestDTO.getPan()));
        if(issuer == null){
            throw new NotFoundException("Issuer doesn't exist.");
        }

//        createTransaction(acquirerTransactionRequestDTO);
//        PaymentStatusDTO paymentStatusDTO = null;
//        try{
        PaymentStatusDTO paymentStatusDTO = restTemplate.postForObject(issuer.getApiUrl(),
                    acquirerTransactionRequestDTO,
                    PaymentStatusDTO.class);
//        }catch ( e){
//            System.out.println(e.getMessage());
//            throw new BadRequestException(e.getMessage());
//        }

        if(paymentStatusDTO == null){
            throw new NotFoundException("Issuer return null.");
        }

        updateTransaction(paymentStatusDTO);
        return paymentStatusDTO;
    }

    private Transaction createTransaction(AcquirerTransactionRequestDTO acquirerTransactionRequestDTO){
        Transaction transaction = new Transaction();
        transaction.setAcquirerInn(acquirerTransactionRequestDTO.getAcquirerInn());
        transaction.setAcquirerOrderId(acquirerTransactionRequestDTO.getAcquirerOrderId());
        transaction.setAcquirerTimestamp(acquirerTransactionRequestDTO.getAcquirerTimestamp());
        transaction.setAmount(acquirerTransactionRequestDTO.getAmount());
        transaction.setTransactionStatus(TransactionStatus.OPEN);
        return transactionRepository.save(transaction);
    }

    private Transaction updateTransaction(PaymentStatusDTO paymentStatusDTO){
        Transaction transaction = transactionRepository.findByAcquirerOrderId(paymentStatusDTO.getAcquirerOrderId());
        transaction.setIssuerOrderId(paymentStatusDTO.getIssuerOrderId());
        transaction.setIssuerTimestamp(paymentStatusDTO.getIssuerTimestamp());
        transaction.setTransactionStatus(paymentStatusDTO.getTransactionStatus());
        return transactionRepository.save(transaction);
    }

    private String getInnFromPan(String pan){
        if(pan.length() < 6){
            throw new InvalidDataException("Invalid pan.");
        }
        return pan.substring(0, 6);
    }
}
