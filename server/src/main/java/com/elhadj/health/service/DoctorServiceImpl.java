package com.elhadj.health.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
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
	DoctorDAO doctorDAO;
	
	@Autowired
	UserDAO userDAO;

	public DoctorDTO getById(long id) throws Exception {
		User user = null;
		try {
			user = userDAO.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new SHRuntimeException(404, "utilisateur non trouvé", "no user match the path paramte id");
		}
		DoctorDTO dto = JavaUtil.convertTo(user, DoctorDTO.class);
		dto.setPassword(null);
		dto.setPresentation(user.getDoctorInfos().getPresentation());
		dto.setSpeciality(user.getDoctorInfos().getSpeciality());
		dto.setMeanOfPayment(user.getDoctorInfos().getMeanOfPayment());
		dto.setPrices(user.getDoctorInfos().getPrices());
		dto.setDiplomas(user.getDoctorInfos().getDiplomas());
		
		return dto;
	}

	@Override
	public List<DoctorDTO> getSeverall(String name, String speciality, String city) throws Exception {
		List<User> doctors = doctorDAO.getByCriteria(name, speciality, city);
		List<DoctorDTO> dtos = new ArrayList<DoctorDTO>();
		for (User d: doctors) {
			DoctorDTO dto = JavaUtil.convertTo(d, DoctorDTO.class);
			dto.setPresentation(d.getDoctorInfos().getPresentation());
			dto.setSpeciality(d.getDoctorInfos().getSpeciality());
			dto.setMeanOfPayment(d.getDoctorInfos().meanOfPayment);
			dto.setPrices(d.getDoctorInfos().getPrices());
			dto.setDiplomas(d.getDoctorInfos().getDiplomas());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public UpdateDoctorDTO update(long doctorId, UpdateDoctorDTO dto) throws Exception {
		User doctor = null;
		try {
			doctor = userDAO.findById(doctorId).get();
		} catch (NoSuchElementException e) {
			throw new SHRuntimeException(404, "utilisateur non trouvé", "no user match the path paramte id");
		}
		DoctorInfos doctorInfos = doctor.getDoctorInfos();
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
		doctor.addDoctorInfos(doctorInfos);
		userDAO.save(doctor);
		UpdateDoctorDTO res = JavaUtil.convertTo(doctor, UpdateDoctorDTO.class);
		res.setPresentation(doctorInfos.getPresentation());
		res.setSpeciality(doctorInfos.getSpeciality());
		res.setMeanOfPayment(doctorInfos.getMeanOfPayment());
		res.setPrices(doctorInfos.getPrices());
		res.setDiplomas(doctorInfos.getDiplomas());
		
		return res;
	}
}
