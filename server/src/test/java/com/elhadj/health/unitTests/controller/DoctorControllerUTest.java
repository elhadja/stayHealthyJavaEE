package com.elhadj.health.unitTests.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.elhadj.health.Util;
import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.dto.SlotDTO;
import com.elhadj.health.model.Slot;
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
	
	@Test
	public void it_should_get_doctors() throws Exception {
			long id = Util.addUser("oups@gmail.com", SHConstants.PATIENT, userService);
			String token = Util.logUser("oups@gmail.com", mockMvc);
			List<SlotDTO> slots = new ArrayList<>();
			slots.add(new SlotDTO());
			slots.add(new SlotDTO());
			when(slotService.getByCriteria(id, "2021-05-16")).thenReturn(slots);
		
			MvcResult res = mockMvc.perform(get("/doctors/" + id + "/slots?date=2021-05-16")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

			List<Slot> getRes = Util.parseResponse(res, slots.getClass());
			Assert.isTrue(getRes.size() == 2);

	}
	
	@Test
	public void it_should_fail_get_doctors() throws Exception {
			long id = Util.addUser("oups@gmail.com", SHConstants.PATIENT, userService);
			String token = Util.logUser("oups@gmail.com", mockMvc);
			List<Slot> slots = new ArrayList<>();
			slots.add(new Slot());
			slots.add(new Slot());
			when(slotService.getByCriteria(id, "2021?05-16")).thenThrow(new SHRuntimeException(400, "", ""));
		
			mockMvc.perform(get("/doctors/" + id + "/slots?date=2021?05-16")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@AfterEach
	public void cleanDB() {
		userService.deleteAll();
	}
}
