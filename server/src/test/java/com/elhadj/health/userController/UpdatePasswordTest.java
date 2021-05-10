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
import com.elhadj.health.model.User;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UpdatePasswordTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	final private User user = new User("firstName", "lastName", "email@springboots.com", "password");
	private String token;
	private long id;
	
	@BeforeEach
	public void init() throws Exception {
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), user.getPassword());
		id = userService.addUser(user);

		MvcResult res = mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		token = SignupTest.parseResponse(res, String.class);
	}
	
	@AfterEach
	public void clean_() throws Exception {
		userService.deleteUser(id);
	}
	
	@Test
	public void it_should_succed() throws Exception {
		mockMvc.perform(put("/users/" + id + "/password")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content("newPassword")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}