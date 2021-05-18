package com.elhadj.health.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.dao.DoctorCustom;
import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.model.Doctor;
import com.elhadj.health.util.JavaUtil;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	DoctorCustom doctorDAO; // TODO find how to use DoctorDAO as type

	public DoctorDTO getById(long id) throws Exception {
		Doctor doctor = null;
		doctor = doctorDAO.getById(id);
		DoctorDTO dto = new DoctorDTO();
		return dto;
	}

	@Override
	public List<DoctorDTO> getSeverall(String name, String speciality, String city) throws Exception {
		List<Doctor> doctors = doctorDAO.getByCriteria(name, speciality, city);
		List<DoctorDTO> dtos = new ArrayList<DoctorDTO>();
		for (Doctor d: doctors) {
			dtos.add(JavaUtil.convertTo(d, DoctorDTO.class));
		}
		return dtos;
	}
}
