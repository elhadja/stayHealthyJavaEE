package com.elhadj.health.dao;

import java.util.List;

import com.elhadj.health.model.Appointment;

public interface AppointmentDAOCustom {
	List<Appointment> findByCriteria(long userId, String startDate, String endDate, String columnName);
}
