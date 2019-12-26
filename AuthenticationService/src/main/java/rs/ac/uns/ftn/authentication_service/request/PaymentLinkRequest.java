package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class PaymentLinkRequest {

	private String token;
	private String type;

	public PaymentLinkRequest(String token, String type) {
		super();
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
