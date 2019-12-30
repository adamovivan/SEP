package rs.ac.uns.ftn.paypal_service.dto.request;

import lombok.Data;

@Data
public class PaymentCompleteRequest {
	
	private String token;
	private String paymentID;
	private String payerID;
	
}
