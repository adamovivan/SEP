package rs.ac.uns.ftn.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private Boolean success;
    private String url;
}
