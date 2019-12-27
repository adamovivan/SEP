package rs.ac.uns.ftn.bank_service.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private String merchantId;
    private String merchantPassword;
    private Double amount;
    private String merchantOrderId;
    private LocalDateTime merchantTimestamp;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
}
