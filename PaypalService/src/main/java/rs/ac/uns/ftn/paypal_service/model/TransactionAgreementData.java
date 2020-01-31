package rs.ac.uns.ftn.paypal_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class TransactionAgreementData {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String token;
	
	@Column(nullable=false)
	private String time;
	
	@Column(nullable=false)
	private String status;
	
	@Column
	private String orderID;
	
	@Column
	private String callbackUrl;
	
	@Column
	private String Notification;

	@Column
	private String planID;
}
