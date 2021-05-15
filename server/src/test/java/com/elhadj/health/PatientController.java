package com.elhadj.health;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.awt.desktop.UserSessionListener;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.LoginResponseDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UserDTO;
import com.elhadj.health.model.User;
import com.elhadj.health.service.PatientService;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class PatientController {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	UserService userService;
	
	private SignupRequestDTO signupRequestDTO;
	private String token = null;
	private Util userUtil = new Util();
	private long id;
	
	public PatientController() {
		signupRequestDTO = new SignupRequestDTO();
		signupRequestDTO.setFirstName("firstname");
		signupRequestDTO.setLastName("lastName");
		signupRequestDTO.setEmail("testSpringBoots.javaee");
		signupRequestDTO.setPassword("password");
	}

	
	@BeforeEach
	public void beforeEachTest() throws Exception {
		id = userService.addUser(signupRequestDTO);
		LoginRequestDTO input = new LoginRequestDTO(signupRequestDTO.getEmail(),
				signupRequestDTO.getPassword());

		MvcResult res = mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		token = Util.parseResponse(res, LoginResponseDTO.class).getToken();
	}
	
	@AfterEach
	public void cleanDataBase() {
		userService.deleteAll();
	}
	
	@Test
	public void getUserById_should_succed() throws Exception {
		MvcResult res = mockMvc.perform(get("/patients/" + id)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test void getUserById_should_fail_if_user_not_exists() throws Exception {
		mockMvc.perform(get("/patients/" + 0)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden())
			.andReturn();
	}
}
