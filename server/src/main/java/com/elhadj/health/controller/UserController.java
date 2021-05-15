package com.elhadj.health.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dto.CreateRessouceResponseDTO;
import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.LoginResponseDTO;
import com.elhadj.health.dto.RequestErrorDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UpdateCredentialsRequestDTO;
import com.elhadj.health.dto.UpdatePasswordRequestDTO;
import com.elhadj.health.model.CustomUserDetails;
import com.elhadj.health.model.User;
import com.elhadj.health.service.UserService;
import com.elhadj.health.util.JwtToken;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	User user;
	
	@Autowired
	JwtToken jwt;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> signup(@RequestBody SignupRequestDTO input) throws Exception {
		long addedUserId = 0;
		try {
			user.setAddress(input.getAddress());
			user.setFirstName(input.getFirstName());
			user.setLastName(input.getLastName());
			user.setEmail(input.getEmail());
			user.setPassword(input.getPassword());
			user.setUserType(input.getUserType());

			addedUserId = userService.addUser(user);
		} catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
								 .body(new RequestErrorDTO(e.getStatusCode(), 
										 e.getMessage(),
										 e.getMessageDescription()));
		} catch (Exception e) {
			return ResponseEntity.status(500)
								 .body(new RequestErrorDTO());	
		}
		
		return ResponseEntity.status(201).body(
				new CreateRessouceResponseDTO(201, addedUserId));
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO input) throws Exception {
		try {
			authManager.authenticate(
				new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
			); 
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(401)
								 .body(new RequestErrorDTO(401, e.getMessage(),""));
		} catch (Exception e) {
			return ResponseEntity.status(500)
								 .body(new RequestErrorDTO());	

		}
		
		CustomUserDetails user = (CustomUserDetails) userService.loadUserByUsername(input.getEmail());
		
		final UserDetails userDetails = userService.loadUserByUsername(input.getEmail());
		final String token = jwt.generateToken(userDetails);
		return ResponseEntity.ok(new LoginResponseDTO(token, user.getUserId()));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteuser(@PathVariable String id, Principal principal) {
		try {
			long _id = Long.parseLong(id);
			isCurrentUserRessource(principal, _id);
			userService.deleteUser(_id);
		} catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
					.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}
		catch (Exception e) {
				return ResponseEntity.status(500)
								 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}
		
		return ResponseEntity.status(200).build();
	}
	
	@RequestMapping(value = "/{id}/password", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDTO input, @PathVariable String id, Principal principal) throws Exception {
		try {
			long userId = Long.parseLong(id);
			isCurrentUserRessource(principal, userId);
			input.isValid();
			userService.updateUserPassword(userId, input);
		}catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
				.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}catch (Exception e) {
			return ResponseEntity.status(500)
						 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}
		return ResponseEntity.status(200).build();
	}
	
	@RequestMapping(value = "/{id}/credentials", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCredentials(@RequestBody UpdateCredentialsRequestDTO input, @PathVariable String id, Principal principal) throws Exception {
		try {
			long userId = Long.parseLong(id);
			isCurrentUserRessource(principal, userId);
			input.validate();
			userService.updateCredentials(userId, input.getEmail(), input.getTel());
		}catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
				.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}catch (Exception e) {
			return ResponseEntity.status(500)
						 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}
		return ResponseEntity.status(200).build();
	}
	
	@RequestMapping() 
	public ResponseEntity<?> notFound() {
		return ResponseEntity.status(404).body("uri not found");
	}
	
	private void isCurrentUserRessource(Principal principal, long id) throws SHRuntimeException {
		CustomUserDetails user = (CustomUserDetails) userService.loadUserByUsername(principal.getName());
		if (user.getUserId() != id) {
			throw new SHRuntimeException(403, "You are not authorized to perform this action", "the id of the logged user do not match the path parameter id");
		}
	}
}
