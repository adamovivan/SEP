package rs.ac.uns.ftn.authentication_service.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {
	
	private Boolean success;
	private String url;

}
