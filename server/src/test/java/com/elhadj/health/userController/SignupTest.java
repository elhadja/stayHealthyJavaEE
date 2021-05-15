package com.elhadj.health.userController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.elhadj.health.Util;
import com.elhadj.health.dto.CreateRessouceResponseDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.model.User;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)	
public class SignupTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void signupTest() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO("firstName", "lastName", "email", "password", "patient");

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
	}
	
	/*
	 * it should fail when required parameters not set
	 */
	@Test
	public void signupTest2() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO(null, "lastName", "email2", "password", "patient");

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	/*
	 * should fail when the user already exists
	 */
	@Test void signupTest3() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO("firstName", "lastName", "email2", "password", "patient");
		userService.addUser(new User("xxxx", "yyyy", input.getEmail(), "password"));

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@AfterEach
	public void clean() throws Exception {
		userService.deleteAll();
	}
}
