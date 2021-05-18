package com.elhadj.health.service;

import java.util.List;

import com.elhadj.health.dto.DoctorDTO;

public interface DoctorService {
	public DoctorDTO getById(long id) throws Exception;
	public List<DoctorDTO> getSeverall(String id, String speciality, String city) throws Exception;
}
