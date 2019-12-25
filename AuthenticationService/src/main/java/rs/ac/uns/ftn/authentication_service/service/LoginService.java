package rs.ac.uns.ftn.authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.authentication_service.config.Password;
import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.repository.ClientRepository;
import rs.ac.uns.ftn.authentication_service.request.LoginRequest;
import rs.ac.uns.ftn.authentication_service.response.LoginResponse;

@Service
public class LoginService {
	@Autowired
	ClientRepository clientRepository;
	
	public LoginResponse login(LoginRequest loginRequest) throws Exception {
		Client client  = clientRepository.findByUsername(loginRequest.getUsername());
		if(Password.check(loginRequest.getPassword(), client.getPassword())) {
			return new LoginResponse(true,loginRequest.getUsername());
		}
		return new LoginResponse(false,"");
		
	}
}
