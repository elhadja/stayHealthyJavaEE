package com.elhadj.health.dao;

import com.elhadj.health.model.Doctor;

public interface DoctorCustom {
	public Doctor getById(long id) throws Exception;
}
