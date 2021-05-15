package com.elhadj.health.service;

import com.elhadj.health.dto.DoctorDTO;

public interface DoctorService {
	public DoctorDTO getById(long id) throws Exception;
}
