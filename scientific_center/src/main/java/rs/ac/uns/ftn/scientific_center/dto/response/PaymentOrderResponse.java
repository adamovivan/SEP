package rs.ac.uns.ftn.scientific_center.dto.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PaymentOrderResponse {
    @NotNull
    private Boolean success;
    private String url;

    public PaymentOrderResponse(){

    }

    public PaymentOrderResponse(Boolean success, String url) {
        this.success = success;
        this.url = url;
    }
}
