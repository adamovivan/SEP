package rs.ac.uns.ftn.authentication_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

	private boolean status;
	private String logedUsername;
	
	
}
