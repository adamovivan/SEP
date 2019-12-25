package rs.ac.uns.ftn.authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.authentication_service.config.Password;
import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.repository.ClientRepository;

@Service
public class RegistrationService {

	@Autowired
	ClientRepository registrationRepository;
	
	public Client save(Client client) throws Exception {
		Client newClient  = client;
		newClient.setPassword(Password.getSaltedHash(client.getPassword()));
		return registrationRepository.save(newClient);
	}

}
