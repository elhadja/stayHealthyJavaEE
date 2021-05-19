package com.elhadj.health.dao;

import java.util.List;
import java.util.function.Predicate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.elhadj.health.model.Doctor;
import com.elhadj.health.model.DoctorInfos;
import com.elhadj.health.model.User;

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
	
	public List<Doctor> getByCriteria(String name, String speciality, String city) throws Exception {
		/*
		CriteriaBuilder cb = em.getCriteriaBuilder();  // TODO use criteria
		CriteriaQuery<User> userCriteria = cb.createQuery(User.class);
		Root<User> root = userCriteria.from(User.class);
		userCriteria.select(root);
		
		Join<User, DoctorInfos> doctorInfosJoin = root.join("doctorInfos");

		javax.persistence.criteria.Predicate p1 = cb.equal(root.get("firstName"), name);
		javax.persistence.criteria.Predicate p2 = cb.equal(doctorInfosJoin.get("speciality"), speciality);
		javax.persistence.criteria.Predicate p12 = cb.and(p1, p2);
		userCriteria.where(p12);

		System.out.println("==> ");
		List<Doctor> doctors = em.createQuery(userCriteria).getResultList();
		*/
		String sql = "select * "
				+ "from User inner join doctor_infos inner join address "
				+ "on user.id = doctor_infos.user_id and user.address_id = address.id ";
		if (name != null || speciality != null || city != null) {
			sql += "where ";
			if (name != null) {
				sql += "(first_name=:first_name ";
				sql += "or last_name=:last_name) ";
			}
			if (speciality != null) {
				if (name != null) {
					sql += "and ";
				}
				sql += "speciality=:speciality ";
			}
			if (city != null) {
				if (name != null || speciality != null) {
					sql += "and ";
				}
				sql += "city=:city";
			}
		}
		Query query = em.createNativeQuery(sql, Doctor.class);
		if (name != null) {
			query.setParameter("first_name", name);
			query.setParameter("last_name", name);
		}
		if (speciality != null) {
			query.setParameter("speciality", speciality);
		}
		if (city != null) {
			query.setParameter("city", city);
		}

		List<Doctor> doctors = (List<Doctor>) query.getResultList();
		return doctors;
	}
}