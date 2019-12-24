package rs.ac.uns.ftn.paypal_service.model;

import lombok.Data;

@Data
public class Order {
	private Double totalPrice;
	private String clientId;
	private String clientSecret;
	private String successUrl;
	private String cancelUrl;
	
	
}
