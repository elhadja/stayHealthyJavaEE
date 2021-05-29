package com.elhadj.health.unitTests.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.UserDTO;
import com.elhadj.health.model.User;
import com.elhadj.health.service.UserService;

@SpringBootTest // TODO find the best config
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	@MockBean
	UserDAO userDAO;

	@Test
	public void it_should_load_a_user_by_id() throws Exception {
		User user = new User();
		user.setEmail("test@gmail.com");
		when(userDAO.findById(1L)).thenReturn(Optional.of(user));
		
		UserDTO dto = userService.loadUserById(1);
		Assert.isTrue(dto.getEmail().equals(user.getEmail()), "bla, bla, bla");
		
	}
}
