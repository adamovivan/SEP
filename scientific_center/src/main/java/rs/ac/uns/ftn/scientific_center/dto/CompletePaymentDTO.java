package rs.ac.uns.ftn.scientific_center.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.scientific_center.model.TransactionStatus;

@Data
@AllArgsConstructor
public class CompletePaymentDTO {
    private String transactionId;
    private TransactionStatus transactionStatus;
}
