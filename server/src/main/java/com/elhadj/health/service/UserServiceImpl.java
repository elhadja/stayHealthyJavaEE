package com.elhadj.health.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dao.DoctorInfosDAO;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UpdatePasswordRequestDTO;
import com.elhadj.health.dto.UserDTO;
import com.elhadj.health.model.CustomUserDetailsImpl;
import com.elhadj.health.model.DoctorInfos;
import com.elhadj.health.model.User;
import com.elhadj.health.util.JavaUtil;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DoctorInfosDAO doctorInfosDAO;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public long addUser(SignupRequestDTO input) throws Exception {
		User user =  null;
		try {
			user = JavaUtil.convertTo(input, User.class);
			user.setPassword(encoder.encode(user.getPassword()));
			if (!user.getIsPatient() || true) {
				DoctorInfos di = new DoctorInfos();
				user.addDoctorInfos(di);
			}
			userDAO.save(user);
		} catch (Exception e) {
			throw new SHRuntimeException(403, e.getMessage(), "email field must be unique");
		}
		return user.getId();
	}
	
	public void deleteUser(long id) throws Exception {
		userDAO.deleteById(id);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			User u = userDAO.loadUserByEmail(email);
			return new CustomUserDetailsImpl(u.getEmail(), u.getPassword(), u.getId(), (Collection<? extends GrantedAuthority>) new ArrayList<GrantedAuthority>());
		} catch (Exception e) {
			throw new UsernameNotFoundException("user not found");
		}
	}	
	
	public void updateUserPassword(long userId, UpdatePasswordRequestDTO input) {
		int n = 0;
		UserDTO userDTO = loadUserById(userId);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!encoder.matches(input.getPassword(), userDTO.getPassword())) {
			throw new SHRuntimeException(403, "mot de passe incorrect", "password not match current password");
		}
		n = userDAO.updateUserPassword(userId, encoder.encode(input.getNewPassword()));
		if (n == 0) {
			throw new SHRuntimeException(400, "No user found", "user not found");
		}
	}
	
	public void updateCredentials(long userId, String newEmail, String newTel) throws Exception {
		try {
			userDAO.updateUserCredentials(userId, newEmail, newTel);
		} catch (Exception e) {
			throw new SHRuntimeException(400, "cet email est déjà utilisé", "email should be unique");
		}
	}
	
	public UserDTO loadUserById(long id) {
		User user = userDAO.findById(id).get();
		UserDTO userDTO = JavaUtil.convertTo(user, UserDTO.class);
		return userDTO;
	}
	
	public void deleteAll() {
		userDAO.deleteAll();
	}
	
}