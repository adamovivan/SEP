package rs.ac.uns.ftn.paypal_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity
public class PaypalPayment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(nullable=false,length=100)
	private String paymentId;
	
	@Column(nullable=false,length=100)
	private String paymentSecret;
	
	@Column(nullable=true)
	private String username;
	
}
