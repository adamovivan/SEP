package rs.ac.uns.ftn.authentication_service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(nullable=false,length=100)
	private String username;
	
	@Column(nullable=false,length=100)
	private String password;
	
	@Column(nullable=false,length=100)
	private String firstName;

	@Column(nullable=false,length=100)
	private String lastName;
	
	@Column(length=100)
	private String address;
	
	@Column(length=100)
	private String email;
	
}
