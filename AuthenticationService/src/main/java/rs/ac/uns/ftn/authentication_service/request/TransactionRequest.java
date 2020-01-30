package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class TransactionRequest {
	private Double totalPrice;
	private String callbackUrl;
	private String orderId;
	private String email;
}
