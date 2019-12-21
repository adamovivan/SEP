package rs.ac.uns.ftn.authentication_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.authentication_service.model.Client;
import rs.ac.uns.ftn.authentication_service.service.RegistrationService;

@RestController
public class RegistrationController {

	@Autowired
	RegistrationService registrationService;

	@PostMapping("/registerClient")
	public ResponseEntity<Client> saveKorisnik(@RequestBody Client client){
		return new ResponseEntity<Client>(registrationService.save(client), HttpStatus.OK);
	}

}
