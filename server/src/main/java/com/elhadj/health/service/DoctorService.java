package com.elhadj.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.dao.DoctorCustom;
import com.elhadj.health.model.Doctor;

@Service
public class DoctorService {
	@Autowired
	DoctorCustom doctorDAO;

	public Doctor getById(long id) throws Exception {
		Doctor doctor = null;
		doctor = doctorDAO.getById(id);
		doctor.setPassword(null);
		return doctor;
	}
}
