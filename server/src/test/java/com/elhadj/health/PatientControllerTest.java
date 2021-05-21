package com.elhadj.health;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.elhadj.health.dto.AddressDTO;
import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.LoginResponseDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UpdatePatientRequestDTO;
import com.elhadj.health.service.PatientService;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class PatientControllerTest {
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
	
	public PatientControllerTest() {
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
	
	@Test void it_should_update_a_patient() throws Exception {
		mockMvc.perform(get("/patients/" + 0)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden())
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
	
	@Test
	void update_patient_names_should_succed() throws Exception {
		final long addedUserId = Util.addUser2("update_patient@gmail.com", userService);
		final String token = Util.logUser("update_patient@gmail.com", mockMvc);
		final UpdatePatientRequestDTO body = new UpdatePatientRequestDTO();
		body.setFirstName("elhadj");
		body.setLastName("bah");

		MvcResult res = mockMvc.perform(put("/patients/" + addedUserId)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(Util.asJsonString(body))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		UpdatePatientRequestDTO dto = Util.parseResponse(res, UpdatePatientRequestDTO.class);
		Assert.isTrue(dto instanceof UpdatePatientRequestDTO);
		Assert.isTrue(dto.getFirstName().equals("elhadj"));
		Assert.isTrue(dto.getLastName().equals("bah"));
	}
	
	@Test
	void update_all_patients_infos_should_succed() throws Exception {
		final long addedUserId = Util.addUser2("update_patient@gmail.com", userService);
		final String token = Util.logUser("update_patient@gmail.com", mockMvc);
		final UpdatePatientRequestDTO body = new UpdatePatientRequestDTO();
		body.setFirstName("elhadj");
		body.setLastName("bah");
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setRoad("road of success");
		addressDTO.setPostalCode(64100);
		addressDTO.setCity("success city");
		body.setAddress(addressDTO);

		MvcResult res = mockMvc.perform(put("/patients/" + addedUserId)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(Util.asJsonString(body))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		UpdatePatientRequestDTO dto = Util.parseResponse(res, UpdatePatientRequestDTO.class);
		Assert.isTrue(dto instanceof UpdatePatientRequestDTO);
		Assert.isTrue(dto.getFirstName().equals("elhadj"));
		Assert.isTrue(dto.getLastName().equals("bah"));
		Assert.isTrue(dto.getAddress().getCity().equals("success city"));
	}
	
	@Test
	void update_patient_should_fail_if_request_body_is_not_valid() throws Exception {
		final long addedUserId = Util.addUser2("update_patient@gmail.com", userService);
		final String token = Util.logUser("update_patient@gmail.com", mockMvc);
		final UpdatePatientRequestDTO body = new UpdatePatientRequestDTO();
		body.setFirstName("elhadj");

		MvcResult res = mockMvc.perform(put("/patients/" + addedUserId)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(Util.asJsonString(body))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andReturn();
	}
}
