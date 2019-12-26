package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class PaymentLinkRequest {

	private String token;
	private String type;
}
