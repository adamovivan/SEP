package rs.ac.uns.ftn.pcc.model;

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
    private String acquirerInn;
    @NotNull
    private String acquirerOrderId;
    @NotNull
    private LocalDateTime acquirerTimestamp;
    private String issuerOrderId;
    private LocalDateTime issuerTimestamp;
    @NotNull
    private Double amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
}
