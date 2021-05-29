package com.elhadj.health.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.dto.RequestErrorDTO;
import com.elhadj.health.dto.UpdateDoctorDTO;
import com.elhadj.health.model.CustomUserDetails;
import com.elhadj.health.model.Slot;
import com.elhadj.health.service.DoctorService;
import com.elhadj.health.service.SlotService;
import com.elhadj.health.service.UserServiceImpl;

@RestController
@RequestMapping(value = "/doctors")
public class DoctorController {
	@Autowired
	DoctorService doctorService;
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	SlotService slotService;

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable String id, Principal principal) {
		DoctorDTO doctor = null;
		try {
			long userId = Long.parseLong(id);
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
	
	@GetMapping
	public ResponseEntity<?> getByCriteria(@RequestParam(required = false) String name,
										   @RequestParam(required = false) String speciality ,
										   @RequestParam(required = false) String city,
										   Principal principal) throws Exception {
		List<DoctorDTO> doctors = null;
		try {
			doctors = doctorService.getSeverall(name, speciality, city);
		}catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
				.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}catch (Exception e) {
			return ResponseEntity.status(500)
						 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}

		return ResponseEntity.status(200).body(doctors);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@RequestBody UpdateDoctorDTO input,
									@PathVariable String id,
									Principal principal) throws Exception {
		UpdateDoctorDTO doctor = null;
		try {
			input.validate();
			long _id = Long.parseLong(id);
			isCurrentUserRessource(principal, _id);
			doctor = doctorService.update(_id, input);
		}catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
				.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}catch (Exception e) {
			return ResponseEntity.status(500)
						 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}

		return ResponseEntity.status(200).body(doctor);
	}
	
	@RequestMapping(value = "/{id}/slots", method = RequestMethod.POST)
	public ResponseEntity<?> addSlot(@RequestBody AddSlotRequestDTO input,
									@PathVariable String id,
									Principal principal) throws Exception {
		long slotId = 0;
		try {
			long _id = Long.parseLong(id);
			isCurrentUserRessource(principal, _id);
			slotId = slotService.addSlot(input, _id);
		}catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
				.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}catch (Exception e) {
			return ResponseEntity.status(500)
						 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}

		return ResponseEntity.status(200).body(slotId);
	}
	
	// TODO use request parameter for date
	@GetMapping("{id}/slots")
	public ResponseEntity<?> getSlotsByDoctorandDate(@PathVariable String id, 
									@RequestParam(required = true) String date,
									Principal principal) throws Exception {
		List<Slot> slots = null;
		try {
			long _id = Long.parseLong(id);
			slots = slotService.getByCriteria(_id, date);
		}catch (SHRuntimeException e) {
			return ResponseEntity.status(e.getStatusCode())
				.body(new RequestErrorDTO(e.getStatusCode(), e.getMessage(), e.getMessageDescription()));
		}catch (Exception e) {
			return ResponseEntity.status(500)
						 .body(new RequestErrorDTO(500, e.getMessage(), e.getMessage()));
		}

		return ResponseEntity.status(200).body(slots);
	}


	private void isCurrentUserRessource(Principal principal, long id) throws SHRuntimeException {
		CustomUserDetails user = (CustomUserDetails) userService.loadUserByUsername(principal.getName());
		if (user != null && user.getUserId() != id) {
			throw new SHRuntimeException(403, "You are not authorized to perform this action", "the id of the logged user do not match the path parameter id");
		}
		
		if (user != null && user.getUserType() != 1) {
			throw new SHRuntimeException(403, "Opération valable uniquement pour les docteurs", "Opération valable uniquement pour les docteurs");
		}
	}
}
