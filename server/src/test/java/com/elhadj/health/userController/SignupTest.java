package com.elhadj.health.userController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.function.BiConsumer;

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
	
	private Util util = new Util();
	
	
	@Test
	public void signupTest() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO("firstName", "lastName", "email", "password");

		MvcResult res = mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		CreateRessouceResponseDTO dto = Util.parseResponse(res, CreateRessouceResponseDTO.class);
		util.add(dto.getId());
	}
	
	/*
	 * it should fail when required parameters not set
	 */
	@Test
	public void signupTest2() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO(null, "lastName", "email2", "password");

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	/*
	 * should fail when the user already exists
	 */
	@Test void signupTest3() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO("firstName", "lastName", "email2", "password");
		long id = userService.addUser(new User("xxxx", "yyyy", input.getEmail(), "password"));
		util.add(id);

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@AfterEach
	public void clean() throws Exception {
		BiConsumer<UserService, Long> f = (service, id) -> { 
			try {
				service.deleteUser(id);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		};
		util.deleteAll(f, userService);
	}
}
