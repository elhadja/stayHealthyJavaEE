package com.elhadj.health.userController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class LoginTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	final private User user = new User("firstName", "lastName", "email@springboots.com", "password");
	
	@Test
	public void it_should_succed() throws Exception {
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), user.getPassword());
		long id = userService.addUser(user);

		MvcResult res = mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		userService.deleteUser(id);
	}
	
	@Test
	public void it_should_fail_when_user_not_exists() throws Exception {
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), user.getPassword());

		mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();
	}
	
	@Test
	public void it_should_fail_when_password_is_not_correct() throws Exception {
		long id = userService.addUser(user);
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), "wrong password");

		mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(SignupTest.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();

		userService.deleteUser(id);
	}
}
