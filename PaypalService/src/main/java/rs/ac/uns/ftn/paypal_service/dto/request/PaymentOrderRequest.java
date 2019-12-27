package rs.ac.uns.ftn.paypal_service.dto.request;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private Double totalPrice;
    private String username;
}
