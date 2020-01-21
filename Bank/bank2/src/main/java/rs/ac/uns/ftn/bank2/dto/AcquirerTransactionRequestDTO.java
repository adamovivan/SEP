package rs.ac.uns.ftn.bank2.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AcquirerTransactionRequestDTO {
    @NotNull
    private String acquirerInn;
    @NotNull
    private String acquirerOrderId;
    @NotNull
    private LocalDateTime acquirerTimestamp;
    @NotNull
    private String pan;
    @NotNull
    private String cvv;
    @NotNull
    private String cardholderName;
    @NotNull
    private LocalDate expiryDate;
    @NotNull
    private Double amount;
}
