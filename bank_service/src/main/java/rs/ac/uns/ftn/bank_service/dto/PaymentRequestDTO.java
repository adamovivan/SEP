package rs.ac.uns.ftn.bank_service.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {
    private String merchantId;
    private String merchantPassword;
    private Double amount;
    private String merchantOrderId;
    private LocalDateTime merchantTimestamp;
    private String successUrl;
    private String failedUrl;
    private String errorUrl;
}
