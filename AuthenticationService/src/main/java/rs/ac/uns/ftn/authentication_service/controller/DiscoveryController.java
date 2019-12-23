package rs.ac.uns.ftn.authentication_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.authentication_service.service.DiscoveryService;

@RestController
@CrossOrigin(origins = "", allowedHeaders = "", maxAge = 3600)
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class DiscoveryController {
	
	@Autowired
	private DiscoveryService discoveryService;
	
	@GetMapping("/discovere")
	public ResponseEntity<List<String>> saveKorisnik(){
		return new ResponseEntity<List<String>>(discoveryService.discover(), HttpStatus.OK);
	}
	
}
