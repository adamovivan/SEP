package rs.ac.uns.ftn.payment_concentrator.model;

import lombok.Data;

import java.util.Set;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Data
@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(nullable=false,length=100)
	private String clientUsername;
	
	@Column(nullable=false,length=100)
	private String firstName;

	@Column(nullable=false,length=100)
	private String lastName;
	
	@Column(length=100)
	private String address;

	@Column(nullable = false)
	private Long magazineId;
	
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private Set<Payment> payment;
	
}
