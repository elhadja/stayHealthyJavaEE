package com.elhadj.health.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	long loggedUserId = 0;
	
	@Autowired
	private PasswordEncoder encoder;

	public long addUser(User user) throws Exception {
		user.setPassword(encoder.encode(user.getPassword()));
		User addedUser = null;
		try {
			addedUser = userDAO.save(user);
		} catch (Exception e) {
			throw new SHRuntimeException("User already exists", "email field must be unique");
		}
		return addedUser.getId();
	}
	
	public void deleteUser(long id) throws Exception {
		userDAO.deleteById(id);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		for (User u: userDAO.findAll()) {
			if (u.getEmail().equals(email)) {
				//return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPassword(), (Collection<? extends GrantedAuthority>) new ArrayList<GrantedAuthority>());
				return new CustomUserDetailsImpl(u.getEmail(), u.getPassword(), u.getId(), (Collection<? extends GrantedAuthority>) new ArrayList<GrantedAuthority>());
			}
		}
		throw new UsernameNotFoundException("user not found");
	}	
}