package rs.ac.uns.ftn.scientific_center.dto.request;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private Double totalPrice;
    private Long magazineId;
    private String paymentType;
}
