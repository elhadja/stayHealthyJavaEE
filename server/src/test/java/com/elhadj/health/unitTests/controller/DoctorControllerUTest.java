package com.elhadj.health.unitTests.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.elhadj.health.Util;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.service.DoctorService;
import com.elhadj.health.service.SlotService;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerUTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SlotService slotService;
	
	@MockBean
	private DoctorService doctorService;
	
	@Autowired 
	private UserService userService;
	
	@Test
	public void it_should_add_slot() throws Exception {
		long id = Util.addUser("oups@gmail.com", SHConstants.DOCTOR, userService);
		String token = Util.logUser("oups@gmail.com", mockMvc);
		when(slotService.addSlot(null, id)).thenReturn(1L);
		AddSlotRequestDTO input = new AddSlotRequestDTO();
		input.setDate("2021-05-19");
		input.setStartTime("12:55");
		
		mockMvc.perform(post("/doctors/" + id + "/slots")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@AfterEach
	public void cleanDB() {
		userService.deleteAll();
	}
}
