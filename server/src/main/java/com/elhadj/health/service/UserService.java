package com.elhadj.health.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.model.CustomUserDetailsImpl;
import com.elhadj.health.model.User;

@Service
public class UserService implements UserDetailsService{
	@Autowired
	private UserDAO userDAO;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public long addUser(User user) throws Exception {
		user.setPassword(encoder.encode(user.getPassword()));
		User addedUser = null;
		try {
			addedUser = userDAO.save(user);
		} catch (Exception e) {
			throw new SHRuntimeException(403, "User already exists", "email field must be unique");
		}
		return addedUser.getId();
	}
	
	public void deleteUser(long id) throws Exception {
		userDAO.deleteById(id);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			User u = userDAO.loadUserByEmail(email);
			System.out.println("==> user getted: "+ u.getEmail() + " " + u.getPassword());
			return new CustomUserDetailsImpl(u.getEmail(), u.getPassword(), u.getId(), (Collection<? extends GrantedAuthority>) new ArrayList<GrantedAuthority>());
		} catch (Exception e) {
			throw new UsernameNotFoundException("user not found");
		}
	}	
	
	public void updateUserPassword(long userId, String newPassword) {
		int n = 0;
		n = userDAO.updateUserPassword(userId, encoder.encode(newPassword));
		if (n == 0) {
			throw new SHRuntimeException(400, "No user found", "user not found");
		}
	}
	
	public void updateCredentials(long userId, String newEmail, String newTel) throws Exception {
		userDAO.updateUserCredentials(userId, newEmail, newTel);
	}
}