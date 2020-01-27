package rs.ac.uns.ftn.paypal_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SubscriptionPlan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String description;
	
	@Column(nullable=false)
	private String planID;
	
	@Column(nullable=false)
	private String frequency;
	
	@Column(nullable=false)
	private String frequencyInterval;
	
	@Column(nullable=false)
	private Integer cycles;
	
	@Column(nullable=false)
	private Double value;
	
	@Column(nullable=false)
	private String type;
	
	

}
