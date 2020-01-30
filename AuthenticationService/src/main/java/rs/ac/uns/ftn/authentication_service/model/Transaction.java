package rs.ac.uns.ftn.authentication_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(nullable=false)
	private String uuid;
	@Column(nullable=false)
	private String email;
	@Column(nullable=false)
	private Double totalPrice;
	@Column
	private String type;
	@Column(nullable=false)
	private String orderId;
	@Column(nullable=false)
	private String callbackUrl;
	
}
