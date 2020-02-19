package com.example.ribbonclient;

import java.io.BufferedReader;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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
