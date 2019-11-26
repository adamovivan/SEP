package rs.ac.uns.ftn.payment_concentrator.dto.request;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private Double totalPrice;
    private Long magazineId;
    private String paymentType;
}
