package rs.ac.uns.ftn.bank_service.dto;

import lombok.Data;

@Data
public class PaymentRegistrationDTO {
	private String username;
	private String merchantId;
	private String merchantPassword;
}
