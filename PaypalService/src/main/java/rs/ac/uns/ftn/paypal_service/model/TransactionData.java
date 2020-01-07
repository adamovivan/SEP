package rs.ac.uns.ftn.paypal_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class TransactionData {

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
	
	@Column(nullable=false)
	private Double price;
	
	@Column
	private String buyer;
	
	@Column
	private String orderID;
	
}
