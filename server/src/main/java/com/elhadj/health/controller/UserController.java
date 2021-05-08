package com.elhadj.health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dto.CreateRessouceResponseDTO;
import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.RequestErrorDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.model.User;
import com.elhadj.health.service.UserService;
import com.elhadj.health.util.JwtToken;

@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	User user;
	
	@Autowired
	JwtToken jwt;

	@PostMapping("/users/signup")
	public ResponseEntity<?> signup(@RequestBody SignupRequestDTO input) throws Exception {
		user.setAddress(input.getAddress());
		user.setFirstName(input.getFirstName());
		user.setLastName(input.getLastName());
		user.setEmail(input.getEmail());
		user.setPassword(input.getPassword());
		long addedUserId = 0;
		try {
			addedUserId = userService.addUser(user);
		} catch (SHRuntimeException e) {
			return ResponseEntity.status(403)
								 .body(new RequestErrorDTO(403, 
										 e.getMessage(),
										 e.getMessageDescription()));
		} catch (Exception e) {
			return ResponseEntity.status(403)
								 .body(new RequestErrorDTO());	
		}
		
		return ResponseEntity.status(201).body(
				new CreateRessouceResponseDTO(201, addedUserId));
	}
	
	@PostMapping("/users/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO input) throws Exception {
		try {
			authManager.authenticate(
				new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
			); 
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(400)
								 .body(new RequestErrorDTO(403, e.getMessage(),""));
		} 
		final UserDetails userDetails = userService.loadUserByUsername(input.getEmail());
		final String token = jwt.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}
	
	@GetMapping("/test")
	public String hello() {
		return "hello world";
	}
}
