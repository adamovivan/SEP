package rs.ac.uns.ftn.eureka.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

@RestController
@CrossOrigin(origins = "", allowedHeaders = "", maxAge = 3600)
@RequestMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
public class EurekaStatusService {
	
	@Autowired
	PeerAwareInstanceRegistry registry;

	@GetMapping("/fetchNames")
	public List<String> eurekaApplications() {
	    Applications applications = registry.getApplications();
	    List<String> imena = new ArrayList<>();
	    List<Application> list = applications.getRegisteredApplications();
	    
	    for (Application application : list) {
	    	String[] s = application.getName().split("-");
	    	if(s[0].equals("API")) {
	    		imena.add(s[1].substring(0, 1) + s[1].substring(1).toLowerCase());
	    	}
	    	
		}
	    return imena;
	}
}
