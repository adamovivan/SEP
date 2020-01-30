package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class TransactionRequest {

	private Double totalPrice;
	private String email;
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
