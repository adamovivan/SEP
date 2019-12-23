package rs.ac.uns.ftn.paypal_service.dto.request;

import lombok.Data;

@Data
public class UserDataRequest {

	private String id;
	private String secret;
	private String username;
}
