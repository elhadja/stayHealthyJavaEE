package com.elhadj.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.dao.DoctorCustom;
import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.model.Doctor;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	DoctorCustom doctorDAO;

	public DoctorDTO getById(long id) throws Exception {
		Doctor doctor = null;
		doctor = doctorDAO.getById(id);
		DoctorDTO dto = new DoctorDTO();
		return dto;
	}
}
