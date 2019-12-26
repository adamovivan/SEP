package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class PaymentRequest {

	private String username;
	private String payment;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	
}
