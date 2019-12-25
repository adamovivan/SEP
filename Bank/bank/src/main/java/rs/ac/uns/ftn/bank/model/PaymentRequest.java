package rs.ac.uns.ftn.bank.model;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String merchantId;
    private String merchantPassword;
    private Double amount;
    private String merchantOrderId;
    private LocalDateTime merchantTimestamp;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
    private String paymentUrl;
    private String paymentId;
    private LocalDateTime expiryTime;
    private Boolean used;
}
