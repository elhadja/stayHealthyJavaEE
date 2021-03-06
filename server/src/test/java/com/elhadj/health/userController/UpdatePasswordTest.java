package com.elhadj.health.userController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.elhadj.health.Util;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.LoginResponseDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UpdateCredentialsRequestDTO;
import com.elhadj.health.dto.UpdatePasswordRequestDTO;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class UpdatePasswordTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	Util util = new Util();
	
	final private SignupRequestDTO user;
	private String token = null;
	private Util userUtil = new Util();
	private long id;
	
	public UpdatePasswordTest() {
		user = new SignupRequestDTO();
		user.setFirstName("firstname");
		user.setLastName("lastName");
		user.setEmail("testSpringBoots.javaee");
		user.setPassword("password");
		user.setUserType(SHConstants.PATIENT);
	}
	
	@BeforeEach
	public void init() throws Exception {
		id = userService.addUser(user);
		userUtil.add(id);
		LoginRequestDTO input = new LoginRequestDTO(user.getEmail(), user.getPassword());

		MvcResult res = mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		token = Util.parseResponse(res, LoginResponseDTO.class).getToken();
	}
	
	@AfterEach
	public void clean_() throws Exception {
		userService.deleteAll();
	}
	
	// --------------------- update password tests -----------------------------------
	
	@Test
	public void it_should_succed() throws Exception {
		UpdatePasswordRequestDTO input = new UpdatePasswordRequestDTO(
				user.getPassword(), "bbbbbbbb");
		mockMvc.perform(put("/users/" + id + "/password")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
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
				.content(Util.asJsonString(input))
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
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	// ------------------- update credentials tests -------------------------------------------

	@Test
	public void update_credentials_should_succed() throws Exception {
		UpdateCredentialsRequestDTO input = new UpdateCredentialsRequestDTO();
		input.setEmail(user.getEmail());
		input.setTel("1234567890");

		mockMvc.perform(put("/users/" + id + "/credentials")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	public void update_credentials_should_fail_if_input_is_not_valid() throws Exception {
		UpdateCredentialsRequestDTO input = new UpdateCredentialsRequestDTO();
		input.setEmail(null);
		input.setTel("1234567890");

		mockMvc.perform(put("/users/" + id + "/credentials")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void update_credentials_should_fail_if_email_already_used_by_anoter_user() throws Exception {
		SignupRequestDTO newUser = new SignupRequestDTO();
		newUser.setFirstName("firstname");
		newUser.setLastName("lastName");
		newUser.setEmail("existingEmail@gmail.com");
		newUser.setPassword("password");
		newUser.setUserType(0);
		userService.addUser(newUser);
		UpdateCredentialsRequestDTO input = new UpdateCredentialsRequestDTO();
		input.setEmail(newUser.getEmail());
		input.setTel("1234567890");

		mockMvc.perform(put("/users/" + id + "/credentials")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
	
	/*------------------------- delete user by id ******************************/

	@Test
	public void delete_user_by_id_should_succed() throws Exception {
		final String email = "deleteTest@succed.com";
		long addedUser = Util.addUser2(email, userService);
		String token = Util.logUser(email, mockMvc);

		mockMvc.perform(delete("/users/" + addedUser)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());;
	
	}
	
	@Test
	public void delete_user_by_id_should_fail_if_user_not_exists() throws Exception {
		final long unexistantId = 0;
		mockMvc.perform(delete("/users/" + unexistantId)
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());;
	}
}