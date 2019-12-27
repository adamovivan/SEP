package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private Double totalPrice;
    private String username;    
}

