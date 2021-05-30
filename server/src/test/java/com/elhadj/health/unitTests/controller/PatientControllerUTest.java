package com.elhadj.health.unitTests.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.elhadj.health.Util;
import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dto.AddAppointmentRequestDTO;
import com.elhadj.health.service.AppointmentService;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerUTest {
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	AppointmentService appointmentService;

	@Autowired
	private UserService userService;
	
	@AfterEach
	public void cleanDB() {
		userService.deleteAll();
	}
	
	@Test
	public void it_shouldAddAppointment() throws Exception {
		long id = Util.addUser("oups@testjee.com", SHConstants.PATIENT, userService);
		String token = Util.logUser("oups@testjee.com", mockMvc);
		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		when(appointmentService.addAppointment(input, id)).thenReturn(id);
		
		MvcResult res = mockMvc.perform(post("/patients/" + id + "/appointments")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	@Test
	public void it_should_cancel_appointment() throws Exception {
		long id = Util.addUser("oups@testjee.com", SHConstants.PATIENT, userService);
		String token = Util.logUser("oups@testjee.com", mockMvc);
		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		
		mockMvc.perform(delete("/patients/" + id + "/appointments/" + 1)
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void cancel_appointment_should_throw_404_if_appointment_not_exists() throws Exception {
		long id = Util.addUser("oups@testjee.com", SHConstants.PATIENT, userService);
		String token = Util.logUser("oups@testjee.com", mockMvc);
		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		doThrow(new SHRuntimeException(404, "", "")).when(appointmentService).cancelAppointment(1);
		
		mockMvc.perform(delete("/patients/" + id + "/appointments/" + 1)
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
