package com.elhadj.health.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.elhadj.health.model.Appointment;

public class AppointmentDAOCustomImpl implements AppointmentDAOCustom {
	@PersistenceContext
	EntityManager em;

	@Override
	public List<Appointment> findByCriteria(long userId, String startDate, String endDate, String columnName) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Appointment> query = builder.createQuery(Appointment.class);
		Root<Appointment> root = query.from(Appointment.class);
		
		Predicate predicate = builder.equal(root.get(columnName).get("id"), userId);
		if (startDate != null) {
			predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("slot").get("date"), LocalDate.parse(startDate)));
		}
		if (endDate != null) {
			predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("slot").get("date"), LocalDate.parse(endDate)));
		}
		
		query.where(predicate);
		return em.createQuery(query.select(root)).getResultList();
	}

}
