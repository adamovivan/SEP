package rs.ac.uns.ftn.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private String paymentId;
    private String paymentUrl;
}
