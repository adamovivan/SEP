package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PaymentOrderRequest {
    private String username;
    private String orderId;
    private Double totalPrice;
    private String callbackUrl;
}

