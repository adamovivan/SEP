package rs.ac.uns.ftn.bank.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class PaymentRequestDTO {
    @NotNull
    private String merchantId;
    @NotNull
    private String merchantPassword;
    @Positive
    private Double amount;
    @NotNull
    private String merchantOrderId;
    @NotNull
    private LocalDateTime merchantTimestamp;
    @NotNull
    private String successUrl;
    @NotNull
    private String failedUrl;
    @NotNull
    private String errorUrl;
}
