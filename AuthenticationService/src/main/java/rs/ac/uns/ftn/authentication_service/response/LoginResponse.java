package rs.ac.uns.ftn.authentication_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginResponse {

	private boolean status;
	private String logedUsername;
	
	
	public LoginResponse(boolean status, String logedUsername) {
		super();
		this.status = status;
		this.logedUsername = logedUsername;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getLogedUsername() {
		return logedUsername;
	}
	public void setLogedUsername(String logedUsername) {
		this.logedUsername = logedUsername;
	}
	
	
}
