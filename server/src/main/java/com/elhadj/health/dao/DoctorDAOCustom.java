package com.elhadj.health.dao;

import java.util.List;

import com.elhadj.health.model.Doctor;
import com.elhadj.health.model.UserAccount;

public interface DoctorDAOCustom {
	public List<UserAccount> getByCriteria(String name, String speciality, String city) throws Exception;
}
