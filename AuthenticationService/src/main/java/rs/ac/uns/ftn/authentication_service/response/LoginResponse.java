package rs.ac.uns.ftn.authentication_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.authentication_service.model.Role;

@Data
@AllArgsConstructor
public class LoginResponse {

	private boolean status;
	private String logedUsername;
	private Role role;
	
	
}
