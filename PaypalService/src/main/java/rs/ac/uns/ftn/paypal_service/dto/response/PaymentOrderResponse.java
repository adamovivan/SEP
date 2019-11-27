package rs.ac.uns.ftn.paypal_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentOrderResponse {
    private Boolean success;
    private String url;
}
