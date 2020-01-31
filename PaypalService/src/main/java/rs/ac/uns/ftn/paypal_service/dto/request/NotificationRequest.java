package rs.ac.uns.ftn.paypal_service.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.paypal_service.config.TransactionStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String transactionId;
    private TransactionStatus transactionStatus;
}