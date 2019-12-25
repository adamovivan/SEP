package rs.ac.uns.ftn.bank.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentCardDTO {
    private String pan;
    private String cvv;
    private String cardholderName;
    private LocalDate expiryDate;
    private String paymentId;
}
