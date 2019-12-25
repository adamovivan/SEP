package rs.ac.uns.ftn.authentication_service.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.ac.uns.ftn.authentication_service.repository.PaymentsRepository;

@Service
public class DiscoveryService {
	 
	@Autowired
	 PaymentsRepository paymentsRepository;
	
	 @Autowired
	 private RestTemplate restTemplate;
	//pribavlja sve trenutno registrovane servise na eureci
	public List<String> discover(){
		ResponseEntity<String[]> responseEntity = restTemplate.getForEntity("http://localhost:8761/fetchNames", String[].class);
		List<String> object = Arrays.asList(responseEntity.getBody());
		return object;
	}
}