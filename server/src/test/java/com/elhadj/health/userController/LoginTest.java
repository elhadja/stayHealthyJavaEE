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

import com.elhadj.health.Util;
import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class LoginTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	final private SignupRequestDTO user;
	
	private Util util = new Util();
	
	public LoginTest() {
		user = new SignupRequestDTO();
		user.setFirstName("firstname");
		user.setLastName("lastName");
		user.setEmail("testSpringBoots.javaee");
		user.setPassword("password");
	}
	
	@AfterEach
	public void afterEach() {
		userService.deleteAll();
	}
	
	@Test
	public void it_should_succed() throws Exception {
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), user.getPassword());
		long id = userService.addUser(user);
		util.add(id);

		mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void it_should_fail_when_user_not_exists() throws Exception {
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), user.getPassword());

		mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();
	}
	
	@Test
	public void it_should_fail_when_password_is_not_correct() throws Exception {
		long id = userService.addUser(user);
		util.add(id);
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), "wrong password");

		mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();
	}
}
