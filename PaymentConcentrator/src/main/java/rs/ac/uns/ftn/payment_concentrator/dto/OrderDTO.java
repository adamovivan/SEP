package rs.ac.uns.ftn.payment_concentrator.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Double totalPrice;
    private String intent;
    private String clientId;
    private String clientSecret;
    private String successUrl;
    private String cancelUrl;
}
