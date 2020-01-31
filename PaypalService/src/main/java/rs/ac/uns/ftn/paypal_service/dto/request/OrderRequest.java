package rs.ac.uns.ftn.paypal_service.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
	private Double totalPrice;
	private String clientId;
	private String username;
	private String clientSecret;
	private String successUrl;
	private String cancelUrl;
    private String orderId;
    private String callbackUrl;
}
