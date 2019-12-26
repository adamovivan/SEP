package rs.ac.uns.ftn.authentication_service.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PaymentResponse {

	private String username;
	private List<String> payments;
	
	
	public PaymentResponse(String username, List<String> payments) {
		super();
		this.username = username;
		this.payments = payments;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getPayments() {
		return payments;
	}
	public void setPayments(List<String> payments) {
		this.payments = payments;
	}
	
	
}
