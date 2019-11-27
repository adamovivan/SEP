package rs.ac.uns.ftn.scientific_center.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentOrderResponse {
    private Boolean success;
    private String url;
}
