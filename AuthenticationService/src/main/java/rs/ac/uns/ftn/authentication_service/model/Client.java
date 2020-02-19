package rs.ac.uns.ftn.authentication_service.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column
	private String companyID;

	@Column(nullable=false,length=100, unique = true)
	private String username;
	
	@Column(nullable=false,length=100)
	private String password;

	private String firstName;

	private String companyName;

	private String phoneNumber;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Client> clients;

	private String lastName;

	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Column(length=100)
	private String address;
	
	@Column(length=100)
	private String email;

	public Client(String username, String companyName, String phoneNumber, String password) {
		this.username = username;
		this.companyName = companyName;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}
}
