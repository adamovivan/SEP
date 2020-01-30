package rs.ac.uns.ftn.paypal_service.dto.request;

import lombok.Data;

@Data
public class SubscriptionPlanRequest {

	private String token;
	private String name;
	private String description;
	private String frequency;
	private String frequencyInterval;
	private int cycles;
	private Double amount;
	private String type;
	
	
}
