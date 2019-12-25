package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class PaymentRequest {

	private String username;
	private String payment;
}
