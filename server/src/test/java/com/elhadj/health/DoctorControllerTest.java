package com.elhadj.health;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.LoginResponseDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UpdateDoctorDTO;
import com.elhadj.health.model.Address;
import com.elhadj.health.service.PatientService;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = true)
public class DoctorControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	UserService userService;
	
	Util util = new Util();
	
	private SignupRequestDTO signupRequestDTO;
	private String token = null;
	private long id;
	
	public DoctorControllerTest() {
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
	
	public String logUser(String email) throws Exception {
		LoginRequestDTO input = new LoginRequestDTO(email, "password");

		MvcResult res = mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		return Util.parseResponse(res, LoginResponseDTO.class).getToken();
	}

	
	@Test
	public void getUserById_should_succed() throws Exception {
		MvcResult res = mockMvc.perform(get("/doctors/" + id)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test void getUserById_should_fail_if_user_not_exists() throws Exception {
		mockMvc.perform(get("/doctors/" + 0)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isForbidden())
			.andReturn();
	}
	
	/************************************* get several doctors *$****************************/
	@Test void it_should_get_doctor_by_firstName() throws Exception {
		long id1 = addUser2("getseverallsucced1");
		long id2 = addUser2("getseverallsucced2");
		
		MvcResult res = mockMvc.perform(get("/doctors?name=firstnamespring")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		List<DoctorDTO> list = new ArrayList<DoctorDTO>();
		list = Util.parseResponse(res, list.getClass());
		Assert.isTrue(list.size() == 2, "the liste size should be 2");
	}
	
	@Test void it_should_get_doctor_by_city() throws Exception {
		long id1 = addUser2("getseverallsucced1");
		long id2 = addUser2("getseverallsucced2");
		long id3 = addUser2("getseverallsucced3", "muskCity");
		
		MvcResult res = mockMvc.perform(get("/doctors?city=muskCity")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		List<DoctorDTO> list = new ArrayList<DoctorDTO>();
		list = Util.parseResponse(res, list.getClass());
		Assert.isTrue(list.size() == 1, "the liste size should be 1");
	}
	
	@Test 
	void it_should_get_doctor_by_speciality() throws Exception {
		// TODO when update doctor service will be implemented
	}
	
	@Test
	void it_should_get_doctor_by_name_speciality_and_city() throws Exception {
		// TODO when update doctor service will be implemented
	}
	
	@Test
	void it_should_update_doctor() throws Exception {
		long id = addUser2("updateDoctor@gmail.com");
		String token = logUser("updateDoctor@gmail.com");
		UpdateDoctorDTO body = new UpdateDoctorDTO();
		body.setFirstName("elhadj");
		body.setLastName("bah");
		body.setSpeciality("medecin");
		Set<String> set = new HashSet<String>();
		set.add("cb");
		set.add("especes");
		body.setMeanOfPayment(set);
		
		MvcResult res = mockMvc.perform(put("/doctors/" + id)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(Util.asJsonString(body))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		UpdateDoctorDTO dto = Util.parseResponse(res, UpdateDoctorDTO.class);
		Assert.isTrue(dto.getFirstName().equals("elhadj"));
		// Assert.isTrue(dto.getMeanOfPayment().size() == 2); // TODO to uncomment util getdoctorbyid fixec
	}
	
	@Test
	void it_should_not_update_doctor() throws Exception {
		long id = addUser2("updateDoctor@gmail.com");
		String token = logUser("updateDoctor@gmail.com");
		UpdateDoctorDTO body = new UpdateDoctorDTO();
		body.setFirstName("elhadj");
		body.setLastName("bah");
		body.setSpeciality("medecin");
		Set<String> set = new HashSet<String>();
		body.setMeanOfPayment(set);
		
		mockMvc.perform(put("/doctors/" + id)
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.content(Util.asJsonString(body))
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andReturn();
	}
	
	@Test void get_seveeral_doctors_should_return_emplty_list() throws Exception {
		MvcResult res = mockMvc.perform(get("/doctors?name=xxxxxx")
			.header("Authorization", "Bearer " + token)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andReturn();
		List<DoctorDTO> list = new ArrayList<DoctorDTO>();
		list = Util.parseResponse(res, list.getClass());
		Assert.isTrue(list.isEmpty(), "empty liste");
	}
	
	public long addUser2(String email,
						 String firstName,
						 String lastName,
						 String city) throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO();
		dto.setEmail(email);
		dto.setFirstName(firstName);
		dto.setLastName("lastnamespring");
		dto.setPassword("password");
		Address address = new Address();
		address.setRoad("road of success");
		address.setCity("success");
		address.setPostalCode(33100);
		dto.setAddress(address);
		return userService.addUser(dto);
	}

	
	public long addUser2(String email) throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO();
		dto.setEmail(email);
		dto.setFirstName("firstnamespring");
		dto.setLastName("lastnamespring");
		dto.setPassword("password");
		Address address = new Address();
		address.setRoad("road of success");
		address.setCity("success");
		address.setPostalCode(33100);
		dto.setAddress(address);
		return userService.addUser(dto);
	}
	
	public long addUser2(String email, String city) throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO();
		dto.setEmail(email);
		dto.setFirstName("firstnamespring");
		dto.setLastName("lastnamespring");
		dto.setPassword("password");
		Address address = new Address();
		address.setRoad("road of success");
		address.setCity(city);
		address.setPostalCode(33100);
		dto.setAddress(address);
		return userService.addUser(dto);
	}

}