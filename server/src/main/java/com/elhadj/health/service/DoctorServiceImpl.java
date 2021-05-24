package com.elhadj.health.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.dao.DoctorDAO;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.dto.UpdateDoctorDTO;
import com.elhadj.health.model.Address;
import com.elhadj.health.model.Doctor;
import com.elhadj.health.model.DoctorInfos;
import com.elhadj.health.model.User;
import com.elhadj.health.util.JavaUtil;

@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	DoctorDAO doctorDAO; // TODO find how to use DoctorDAO as type
	
	@Autowired
	UserDAO userDAO;

	public DoctorDTO getById(long id) throws Exception {
		Doctor doctor = null;
		User user = userDAO.findById(id).get();
		DoctorDTO dto = new DoctorDTO();
		if (user != null) {
			dto = JavaUtil.convertTo(user, DoctorDTO.class);
		}

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

	@Override
	public UpdateDoctorDTO update(long doctorId, UpdateDoctorDTO dto) throws Exception {
		User doctor = userDAO.findById(doctorId).get();
		DoctorInfos doctorInfos = doctor.getDoctorInfos();
		
		if (doctor != null) {
			doctor.setFirstName(dto.getFirstName());
			doctor.setLastName(dto.getLastName());
			if (dto.getAddress() != null) {
				dto.getAddress().validate();
				doctor.setAddress(JavaUtil.convertTo(dto.getAddress(), Address.class));
			}
			if (dto.getPresentation() != null) {
				doctorInfos.setPresentation(dto.getPresentation());
			}
			if (dto.getSpeciality() != null) {
				doctorInfos.setSpeciality(dto.getSpeciality());
			}
			if (dto.getMeanOfPayment() != null) {
				doctorInfos.setMeanOfPayment(dto.getMeanOfPayment());
			}
			if (dto.getPrices() != null) {
				doctorInfos.setPrices(dto.getPrices());
			}
			if (dto.getDiplomas() != null) {
				doctorInfos.setDiplomas(dto.getDiplomas());
			}
		}
		doctor.addDoctorInfos(doctorInfos);
		userDAO.save(doctor);
		Doctor updateDoctor = doctorDAO.getById(doctor.getId());
		return JavaUtil.convertTo(updateDoctor, UpdateDoctorDTO.class);
	}
}
