package rs.ac.uns.ftn.authentication_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.authentication_service.config.Password;
import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.repository.ClientRepository;
import rs.ac.uns.ftn.authentication_service.request.LoginRequest;
import rs.ac.uns.ftn.authentication_service.response.LoginResponse;

@Service
public class LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	ClientRepository clientRepository;
	
	public LoginResponse login(LoginRequest loginRequest) throws Exception {
		Client client  = clientRepository.findByUsername(loginRequest.getUsername());
		if(Password.check(loginRequest.getPassword(), client.getPassword())) {
			logger.info("Successfully saved payment information of user " + loginRequest.getUsername());
			return new LoginResponse(true,loginRequest.getUsername());
		}
		logger.error("Storage of payment information for "+ loginRequest.getUsername() + " users was not performed");
		return new LoginResponse(false,"");
		
	}
}
