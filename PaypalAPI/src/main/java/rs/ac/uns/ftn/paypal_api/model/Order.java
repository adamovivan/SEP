package rs.ac.uns.ftn.paypal_api.model;

import lombok.Data;

@Data
public class Order {
	private Double totalPrice;
	private String intent;
	private String clientId;
	private String clientSecret;
	private String successUrl;
	private String cancelUrl;
}
