package rs.ac.uns.ftn.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.bank.model.TransactionStatus;

@Data
@AllArgsConstructor
public class TransactionStatusDTO {
    private String transactionId;
    private TransactionStatus transactionStatus;
}
