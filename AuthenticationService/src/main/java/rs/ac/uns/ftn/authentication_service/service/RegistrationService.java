package rs.ac.uns.ftn.authentication_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.authentication_service.config.Password;
import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.repository.ClientRepository;

@Service
public class RegistrationService {
	
	private static final Logger logger = LoggerFactory.getLogger(PaymentsService.class);

	@Autowired
	ClientRepository registrationRepository;
	
	public Client save(Client client) throws Exception {
		Client newClient  = client;
		newClient.setPassword(Password.getSaltedHash(client.getPassword()));
		try {
			newClient = registrationRepository.save(newClient);
			logger.info("Successfully registered new user" + newClient.getUsername() + ".");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Failed to register user " + newClient.getUsername() + ".");
			
		}
		return newClient;
	}

}
