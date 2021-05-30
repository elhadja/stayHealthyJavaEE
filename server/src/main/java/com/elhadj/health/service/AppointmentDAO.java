package com.elhadj.health.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elhadj.health.dao.AppointmentDAOCustom;
import com.elhadj.health.model.Appointment;

@Repository
public interface AppointmentDAO extends CrudRepository<Appointment, Long>, AppointmentDAOCustom {
	
}
