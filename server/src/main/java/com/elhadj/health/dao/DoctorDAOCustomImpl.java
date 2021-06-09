package com.elhadj.health.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.naming.java.javaURLContextFactory;
import org.springframework.stereotype.Repository;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.model.Doctor;
import com.elhadj.health.model.User;
import com.elhadj.health.util.JavaUtil;

@Repository
public class DoctorDAOCustomImpl implements DoctorDAOCustom {
	@PersistenceContext
	EntityManager em;
	
	public List<User> getByCriteria(String name, String speciality, String city) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> userCriteria = cb.createQuery(User.class);
		Root<User> root = userCriteria.from(User.class);
		
		Predicate predicate = cb.equal(root.get("userType"), SHConstants.DOCTOR);
		if (JavaUtil.notNullAndEmpty(name)) {
			predicate = cb.and(predicate, 
							   cb.or(cb.equal(root.get("firstName"), name), 
									  cb.equal(root.get("lastName"), name)));
		}
		if (JavaUtil.notNullAndEmpty(city)) {
			predicate = cb.and(predicate, cb.equal(root.get("address").get("city"), city));
		}
		if (JavaUtil.notNullAndEmpty(speciality)) {
			predicate = cb.and(predicate, cb.equal(root.get("doctorInfos").get("speciality"), speciality));
		}

		userCriteria.where(predicate);
		
		TypedQuery<User> query = em.createQuery(userCriteria);
		return query.getResultList();
	}
}