package rs.ac.uns.ftn.authentication_service.request;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private Double totalPrice;
    private String username;
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
    
}

