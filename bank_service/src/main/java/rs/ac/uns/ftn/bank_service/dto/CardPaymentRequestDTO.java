package rs.ac.uns.ftn.bank_service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CardPaymentRequestDTO {
    @NotNull
    private String username;
    @NotNull
    private String orderId;
    @Positive
    private Double totalPrice;
    @NotNull
    private String callbackUrl;
}
