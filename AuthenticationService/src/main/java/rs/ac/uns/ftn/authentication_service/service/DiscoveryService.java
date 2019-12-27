package rs.ac.uns.ftn.authentication_service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rs.ac.uns.ftn.authentication_service.repository.PaymentsRepository;

@Service
public class DiscoveryService {
	
	private static final Logger logger = LoggerFactory.getLogger(DiscoveryService.class);
	 
	@Autowired
	 PaymentsRepository paymentsRepository;
	
	 @Autowired
	 private RestTemplate restTemplate;
	//pribavlja sve trenutno registrovane servise na eureci
	public List<String> discover(){
		List<String> object = new ArrayList<>();
		try {
			ResponseEntity<String[]> responseEntity = restTemplate.getForEntity("http://localhost:8761/fetchNames", String[].class);
			object = Arrays.asList(responseEntity.getBody());
			logger.info("Successfully completed delivery of all currently registered payment services");
		} catch (Exception e) {
			logger.info("The delivery of all currently registered payment services has not been completed");
			// TODO: handle exception
		}
		return object;
	}
}