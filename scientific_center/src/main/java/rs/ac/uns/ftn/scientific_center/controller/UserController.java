package rs.ac.uns.ftn.scientific_center.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.scientific_center.dto.JwtTokenDTO;
import rs.ac.uns.ftn.scientific_center.dto.LoginDTO;
import rs.ac.uns.ftn.scientific_center.security.TokenUtils;

@RestController
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<JwtTokenDTO> login(@RequestBody LoginDTO loginDTO) {
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());

		return ResponseEntity.ok(new JwtTokenDTO(tokenUtils.generateToken(details)));
	}

	@RequestMapping("/secured")
	public String secured(){
		String fooResourceUrl
				= "https://localhost:8765";
		ResponseEntity<String> s = restTemplate.getForEntity(fooResourceUrl + "/zuultest", String.class);
		System.out.println(s);

		return s.getBody();
	}

}
