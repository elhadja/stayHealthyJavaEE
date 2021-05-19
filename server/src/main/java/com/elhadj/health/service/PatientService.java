package com.elhadj.health.service;

import com.elhadj.health.dto.PatientDTO;
import com.elhadj.health.dto.UpdatePatientRequestDTO;

public interface PatientService {
	PatientDTO getById(long id);
	UpdatePatientRequestDTO update(UpdatePatientRequestDTO dto, long userId) throws Exception;
}
