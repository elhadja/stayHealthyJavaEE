package com.elhadj.health.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dao.DoctorInfosDAO;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.dto.GetAppointmentDTOResponse;
import com.elhadj.health.dto.SignupRequestDTO;
import com.elhadj.health.dto.UpdatePasswordRequestDTO;
import com.elhadj.health.dto.UserDTO;
import com.elhadj.health.model.Appointment;
import com.elhadj.health.model.CustomUserDetailsImpl;
import com.elhadj.health.model.DoctorInfos;
import com.elhadj.health.model.User;
import com.elhadj.health.util.JavaUtil;

import ch.qos.logback.classic.pattern.Util;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AppointmentDAO appointmentDAO;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public long addUser(SignupRequestDTO input) throws Exception {
		input.validate();
		User user =  null;
		try {
			user = JavaUtil.convertTo(input, User.class);
			user.setPassword(encoder.encode(user.getPassword()));
			if (user.getUserType() == SHConstants.DOCTOR || true) {
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
			return new CustomUserDetailsImpl(u.getEmail(), u.getPassword(), u.getId(), u.getUserType(), (Collection<? extends GrantedAuthority>) new ArrayList<GrantedAuthority>());
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
	
	@Override
	@Transactional
	public List<GetAppointmentDTOResponse> getUserAppointments(long userId, String startDate, String endDate) {
		// TODO replace return type by GetAppointmentDTOResponse
		List<Appointment> appointments = null;
		User user = null;
		try {
			user = userDAO.findById(userId).get();
		} catch (NoSuchElementException e) {
			throw new SHRuntimeException(400, "No user found", "user not found");
		}
		String column = user.getUserType() == SHConstants.PATIENT ? "patient" : "doctor";
		appointments = appointmentDAO.findByCriteria(userId, startDate, endDate, column);
		
		List<GetAppointmentDTOResponse> res = new ArrayList<GetAppointmentDTOResponse>();
		for (Appointment appointment: appointments) {
			GetAppointmentDTOResponse dto = new GetAppointmentDTOResponse();
			dto.setRaison(appointment.getRaison());
			dto.setSlot(JavaUtil.convertTo(appointment.getSlot(), AddSlotRequestDTO.class));
			if (user.getUserType() == SHConstants.PATIENT) {
				dto.setUser(JavaUtil.convertTo(appointment.getPatient(), UserDTO.class));
			} else {
				dto.setUser(JavaUtil.convertTo(appointment.getDoctor(), UserDTO.class));
			}
			dto.getUser().setPassword(null);
			res.add(dto);
		}

		return res;
	}
}