package com.elhadj.health.userController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.LoginResponseDTO;
import com.elhadj.health.dto.UpdatePasswordRequestDTO;
import com.elhadj.health.model.User;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class UpdatePasswordTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	final private User user = new User("firstName", "lastName", "email@springboots.com", "password");
	private String token = null;
	private long id;
	
	@BeforeEach
	public void init() throws Exception {
		id = userService.addUser(user);
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), user.getPassword());

		MvcResult res = mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		token = SignupTest.parseResponse(res, LoginResponseDTO.class).getToken();
	}
	
	@AfterEach
	public void clean_() throws Exception {
		userService.deleteUser(id);
	}
	
	@Test
	public void it_should_succed() throws Exception {
		UpdatePasswordRequestDTO input = new UpdatePasswordRequestDTO(
				user.getPassword(), "bbbbbbbb");
		mockMvc.perform(put("/users/" + id + "/password")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void it_should_fail_if_password_not_match() throws Exception {
		UpdatePasswordRequestDTO input = new UpdatePasswordRequestDTO(
				"wrong-password", "bbbbbbbb");
		mockMvc.perform(put("/users/" + id + "/password")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void it_should_fail_if_input_are_not_valid() throws Exception {
		UpdatePasswordRequestDTO input = new UpdatePasswordRequestDTO(
				user.getPassword(), "");

		mockMvc.perform(put("/users/" + id + "/password")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}