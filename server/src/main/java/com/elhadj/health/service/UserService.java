package com.elhadj.health.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UpdatePasswordRequestDTO;
import com.elhadj.health.dto.UserDTO;

public interface UserService extends UserDetailsService {
	public long addUser(SignupRequestDTO input) throws Exception;
	
	public void deleteUser(long id) throws Exception;
	
	public void updateUserPassword(long userId, UpdatePasswordRequestDTO input);
	
	public void updateCredentials(long userId, String newEmail, String newTel) throws Exception;
	
	public UserDTO loadUserById(long id);
	
	public void deleteAll();
}
