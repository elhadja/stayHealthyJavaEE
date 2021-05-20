package com.elhadj.health.service;

import java.util.List;

import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.dto.UpdateDoctorDTO;

public interface DoctorService {
	public DoctorDTO getById(long id) throws Exception;
	public List<DoctorDTO> getSeverall(String id, String speciality, String city) throws Exception;
	public UpdateDoctorDTO update(long doctorId, UpdateDoctorDTO dto) throws Exception;
}
