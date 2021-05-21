package com.elhadj.health.dao;

import java.util.List;

import com.elhadj.health.model.Doctor;

public interface DoctorDAOCustom {
	public Doctor getById(long id) throws Exception;
	public List<Doctor> getByCriteria(String name, String speciality, String city) throws Exception;
}
