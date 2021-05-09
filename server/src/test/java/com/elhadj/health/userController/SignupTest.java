package com.elhadj.health.userController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.elhadj.health.controller.UserController;
import com.elhadj.health.dto.CreateRessouceResponseDTO;
import com.elhadj.health.dto.RequestErrorDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.model.User;
import com.elhadj.health.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)	
public class SignupTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	List<Long> ids = new ArrayList<>();
	
	@Test
	public void signupTest() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO("firstName", "lastName", "email", "password");

		MvcResult res = mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();

		CreateRessouceResponseDTO dto = parseResponse(res, CreateRessouceResponseDTO.class);
		ids.add(dto.getId());
	}
	
	/*
	 * it should fail when required parameters not set
	 */
	@Test
	public void signupTest2() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO(null, "lastName", "email2", "password");

		MvcResult res = mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	/*
	 * should fail when the user already exists
	 */
	@Test void signupTest3() throws Exception {
		SignupRequestDTO input = new SignupRequestDTO("firstName", "lastName", "email2", "password");
		long id = userService.addUser(new User("xxxx", "yyyy", input.getEmail(), "password"));
		ids.add(id);

		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();
	}
	
	@AfterEach
	public void clean() throws Exception {
		 for (long id: ids) {
			 System.out.println("===> " + id);
			  userService.deleteUser(id);
		 }
		 ids.clear();
	}
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
	    try {
			String contentAsString = result.getResponse().getContentAsString();
	        //return MAPPER.readValue(contentAsString, responseClass);
		    //new ObjectMapper().
			Gson gson = new Gson();
			return gson.fromJson(contentAsString, responseClass);
	    } catch (IOException e) {
	      throw new RuntimeException(e);
	    }
  }
}
