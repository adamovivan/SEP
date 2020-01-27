package rs.ac.uns.ftn.scientific_center.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Transaction {
    @Id
    private Long id;
    private String orderId;
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;
    @ManyToOne
    private User customer;
    @ManyToOne
    private User vendor;
    private Double amount;
    private SubscriptionType subscriptionType;
    @ManyToMany
    private Set<PricelistItem> items;
}
