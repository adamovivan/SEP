package rs.ac.uns.ftn.authentication_service.response;

import lombok.Data;

@Data
public class SubscriptionPlan {
	

	private Long id;
	private String username;
	private String name;
	private String description;
	private String planID;
	private String frequency;
	private String frequencyInterval;
	private Integer cycles;
	private Double value;
	private String type;
	
	

}