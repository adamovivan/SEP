package rs.ac.uns.ftn.bank_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.bank_service.model.TransactionStatus;

@Data
@AllArgsConstructor
public class CompletePaymentDTO {
    private String transactionId;
    private TransactionStatus transactionStatus;
}
