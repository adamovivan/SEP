package rs.ac.uns.ftn.bank_service.dto;


import lombok.Data;

@Data
public class CardPaymentResponseDTO {
    private String paymentUrl;
    private String paymentId;
}
