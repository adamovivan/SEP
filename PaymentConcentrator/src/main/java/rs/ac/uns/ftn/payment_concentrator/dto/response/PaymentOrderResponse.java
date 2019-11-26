package rs.ac.uns.ftn.payment_concentrator.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentOrderResponse {
    private Boolean success;
    private String url;
}
