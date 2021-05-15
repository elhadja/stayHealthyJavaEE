package com.elhadj.health.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.dto.RequestErrorDTO;
import com.elhadj.health.model.CustomUserDetails;
import com.elhadj.health.service.DoctorService;
import com.elhadj.health.service.UserServiceImpl;

@RestController
@RequestMapping(value = "/doctors")
public class DoctorController {
	@Autowired
	DoctorService doctorService;
	
	@Autowired
	UserServiceImpl userService;

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable String id, Principal principal) {
		DoctorDTO doctor = null;
		try {
			long userId = Long.parseLong(id);
			isCurrentUserRessource(principal, userId);
			doctor = doctorService.getById(userId);
		}catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
				.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}catch (Exception e) {
			return ResponseEntity.status(500)
						 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}
		return ResponseEntity.status(200).body(doctor);

	}
	
	private void isCurrentUserRessource(Principal principal, long id) throws SHRuntimeException {
		CustomUserDetails user = (CustomUserDetails) userService.loadUserByUsername(principal.getName());
		if (user.getUserId() != id) {
			throw new SHRuntimeException(403, "You are not authorized to perform this action", "the id of the logged user do not match the path parameter id");
		}
	}


}
