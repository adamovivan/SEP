package rs.ac.uns.ftn.bank_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.bank_service.model.TransactionStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompletePaymentDTO {
    private String transactionId;
    private TransactionStatus transactionStatus;
}
