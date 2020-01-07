package rs.ac.uns.ftn.bank.dto;

import lombok.Data;
import rs.ac.uns.ftn.bank.model.TransactionStatus;

import java.time.LocalDateTime;

@Data
public class PaymentStatusPccDTO {
    private String acquirerOrderId;
    private LocalDateTime acquirerTimestamp;
    private String issuerOrderId;
    private LocalDateTime issuerTimestamp;
    private TransactionStatus transactionStatus;
}
