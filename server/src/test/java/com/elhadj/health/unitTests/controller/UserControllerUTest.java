package com.elhadj.health.unitTests.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.elhadj.health.Util;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dto.GetAppointmentDTOResponse;
import com.elhadj.health.service.UserService;
import com.elhadj.health.service.UserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	//@MockBean // TODO find why mock bean not working in this case
	//private UserService userServiceMock;

	
	@AfterEach
	public void cleanDB() {
		userService.deleteAll();
	}
	
	@Test
	@Disabled
	public void it_should_get_appointements() throws Exception {
		//given
		long id = Util.addUser("oups@testjee.com", SHConstants.PATIENT, userService);
		String token = Util.logUser("oups@testjee.com", mockMvc);
		//when(userServiceMock.getUserAppointments(id, null, null))
		//				.thenReturn(Arrays.asList(new GetAppointmentDTOResponse(), new GetAppointmentDTOResponse()));
		
		// when //then
		MvcResult res = mockMvc.perform(get("/users/" + id + "/appointments")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		List<GetAppointmentDTOResponse> dtos = new ArrayList<GetAppointmentDTOResponse>();
		dtos = Util.parseResponse(res, dtos.getClass());
		Assert.isTrue(dtos.size() == 2, dtos.size() + " should be " + 2);
	}
}
