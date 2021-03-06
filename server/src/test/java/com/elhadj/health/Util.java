package com.elhadj.health;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.dto.AddressDTO;
import com.elhadj.health.dto.LoginRequestDTO;
import com.elhadj.health.dto.LoginResponseDTO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.service.SlotService;
import com.elhadj.health.service.UserService;
import com.elhadj.health.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class Util {
	List<Long> ids = new ArrayList<>();
	UserService userService = new UserServiceImpl();
	
	public void add(long id) {
		ids.add(id);
	}
	
	public <T> void deleteAll(BiConsumer<T, Long> f, T arg) {
		 for (long id: ids) {
			 f.accept(arg, id);
		 }
		 ids.clear();
	}
	
	
	public static long addUser2(String email, String city, UserService userService) throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO();
		dto.setEmail(email);
		dto.setFirstName("firstnamespring");
		dto.setLastName("lastnamespring");
		dto.setPassword("password");
		dto.setUserType(0);
		AddressDTO address = new AddressDTO();
		address.setRoad("road of success");
		address.setCity(city);
		address.setPostalCode(33100);
		dto.setAddress(address);
		return userService.addUser(dto);
	}
	
	public static long addUser2(String email, int userType , String city, UserService userService) throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO();
		dto.setEmail(email);
		dto.setFirstName("firstnamespring");
		dto.setLastName("lastnamespring");
		dto.setPassword("password");
		dto.setUserType(userType);
		AddressDTO address = new AddressDTO();
		address.setRoad("road of success");
		address.setCity(city);
		address.setPostalCode(33100);
		dto.setAddress(address);
		return userService.addUser(dto);
	}
	
	public static long addUser2(String email, UserService userService) throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO();
		dto.setEmail(email);
		dto.setFirstName("firstnamespring");
		dto.setLastName("lastnamespring");
		dto.setPassword("password");
		dto.setUserType(0);
		AddressDTO address = new AddressDTO();
		address.setRoad("road of success");
		address.setCity("success");
		address.setPostalCode(33100);
		dto.setAddress(address);
		return userService.addUser(dto);
	}
	
	public static long addUser(String email, int userType, UserService userService) throws Exception {
		SignupRequestDTO dto = new SignupRequestDTO();
		dto.setEmail(email);
		dto.setFirstName("firstnamespring");
		dto.setLastName("lastnamespring");
		dto.setPassword("password");
		dto.setUserType(userType);
		AddressDTO address = new AddressDTO();
		address.setRoad("road of success");
		address.setCity("success");
		address.setPostalCode(33100);
		dto.setAddress(address);
		return userService.addUser(dto);
	}


	public static String logUser(String email, MockMvc mockMvc) throws Exception {
		LoginRequestDTO input = new LoginRequestDTO(email, "password");

		MvcResult res = mockMvc.perform(post("/users/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Util.asJsonString(input))
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		return Util.parseResponse(res, LoginResponseDTO.class).getToken();
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
	
	public static long addSlot(String date, String time, long id, SlotService slotService) throws Exception {
		AddSlotRequestDTO input = new AddSlotRequestDTO();
		input.setDate(date);
		input.setStartTime(time);
		return slotService.addSlot(input, id);
	}
}