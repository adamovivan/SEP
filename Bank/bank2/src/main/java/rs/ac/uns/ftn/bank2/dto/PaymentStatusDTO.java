package rs.ac.uns.ftn.bank2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.bank2.model.TransactionStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class PaymentStatusDTO {
    private String acquirerOrderId;
    private LocalDateTime acquirerTimestamp;
    private String issuerOrderId;
    private LocalDateTime issuerTimestamp;
    private TransactionStatus transactionStatus;
}
