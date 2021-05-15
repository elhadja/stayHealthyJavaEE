package com.elhadj.health.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.elhadj.health.model.Doctor;

@Repository
public class DoctorCustomDAOImpl implements DoctorCustom {
	@PersistenceContext
	EntityManager em;

	@Override
	public Doctor getById(long id) throws Exception {
		final String sqlQuery = "select * "
				+ "from User left join doctor_infos "
				+ "on User.id = Doctor_infos.id "
				+ "where User.id = :id";

		Query query = em.createNativeQuery(sqlQuery, Doctor.class);
		query.setParameter("id", id);
		Doctor doctor = (Doctor) query.getSingleResult();
		return doctor;
	}

}
