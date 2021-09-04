package com.elhadjium.PMBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elhadjium.PMBackend.dto.ErrorOutputDTO;
import com.elhadjium.PMBackend.dto.signupInputDTO;
import com.elhadjium.PMBackend.entity.User;
import com.elhadjium.PMBackend.exception.PMRuntimeException;
import com.elhadjium.PMBackend.service.UserService;

@RestController
@RequestMapping("pm-api/users")
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("signup")
	public Long signup(@RequestBody signupInputDTO signupInputDTO) throws Exception {
		signupInputDTO.validate();
		User user = new User();
		user.setEmail(signupInputDTO.getEmail());
		user.setPseudo(signupInputDTO.getPseudo());
		user.setPassword(signupInputDTO.getPassword());

		return userService.signup(user);
	}
	
	@GetMapping("/test")
	public String test() {
		return "test ok";
	}
	
	@ExceptionHandler({PMRuntimeException.class})
	public ResponseEntity<?> handleException(PMRuntimeException ex) {
		ErrorOutputDTO errorOutputDTO = new ErrorOutputDTO();
		errorOutputDTO.setMessage(ex.getMessage());
		errorOutputDTO.setMessageDescription(ex.getMessage());

		return ResponseEntity.status(ex.getStatus()).body(errorOutputDTO);
	}

}