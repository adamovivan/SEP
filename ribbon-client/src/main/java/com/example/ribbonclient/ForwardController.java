package com.example.ribbonclient;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ForwardController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/**")
	public ResponseEntity<?> getForward(HttpMethod method, HttpServletRequest request) throws URISyntaxException
	{
	    ResponseEntity<Object> responseEntity = restTemplate.exchange("https://zuul-gateway" + request.getRequestURI(), method, null, Object.class);
	    return ResponseEntity.ok(responseEntity.getBody());
	}
 
	@PostMapping("/**")
	public ResponseEntity<?> postForward(@RequestBody String body, @RequestHeader MultiValueMap<String, String> headers, HttpMethod method, HttpServletRequest request) throws URISyntaxException
	{
	    ResponseEntity<Object> responseEntity = restTemplate.exchange("https://zuul-gateway"  + request.getRequestURI(), method, new HttpEntity<>(body, headers), Object.class);
	    return ResponseEntity.ok(responseEntity.getBody());
	}
	
	@PutMapping("/**")
	public ResponseEntity<?> putForward(@RequestBody String body, @RequestHeader MultiValueMap<String, String> headers, HttpMethod method, HttpServletRequest request) throws URISyntaxException
	{
		ResponseEntity<Object> responseEntity = restTemplate.exchange("https://zuul-gateway" + request.getRequestURI(), method, new HttpEntity<>(body, headers), Object.class);
	    return ResponseEntity.ok(responseEntity.getBody());
	}
	
	@DeleteMapping("/**")
	public ResponseEntity<?> deleteForward(HttpMethod method, HttpServletRequest request) throws URISyntaxException
	{
	    ResponseEntity<Object> responseEntity = restTemplate.exchange("https://zuul-gateway" + request.getRequestURI(), method, null, Object.class);
	    return ResponseEntity.ok(responseEntity.getBody());
	}
	
}
