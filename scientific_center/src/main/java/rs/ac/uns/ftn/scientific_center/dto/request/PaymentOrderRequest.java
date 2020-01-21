package rs.ac.uns.ftn.scientific_center.dto.request;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private Double totalPrice;
    private String email;

    public PaymentOrderRequest(){

    }

    public PaymentOrderRequest(Double totalPrice, String email) {
        this.totalPrice = totalPrice;
        this.email = email;
    }
}
