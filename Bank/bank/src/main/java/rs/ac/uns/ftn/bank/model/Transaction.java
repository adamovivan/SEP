package rs.ac.uns.ftn.bank.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String transactionId;
    @NotNull
    private String merchantId;
    @NotNull
    private Double amount;
    @NotNull
    private String merchantOrderId;
    @NotNull
    private LocalDateTime merchantTimestamp;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
}
