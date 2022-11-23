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
import com.elhadj.health.model.UserAccount;
import com.elhadj.health.util.JavaUtil;

@Repository
public class DoctorDAOCustomImpl implements DoctorDAOCustom {
	@PersistenceContext
	EntityManager em;
	
	public List<UserAccount> getByCriteria(String name, String speciality, String city) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserAccount> userCriteria = cb.createQuery(UserAccount.class);
		Root<UserAccount> root = userCriteria.from(UserAccount.class);
		
		Predicate predicate = cb.equal(root.get("userType"), SHConstants.DOCTOR);
		if (JavaUtil.notNullAndEmpty(name)) {
			predicate = cb.and(predicate, 
							   cb.or(cb.like(root.get("firstName"), name + "%"), 
									  cb.like(root.get("lastName"), name + "%")));
		}
		if (JavaUtil.notNullAndEmpty(city)) {
			predicate = cb.and(predicate, cb.like(root.get("address").get("city"), city + "%"));
		}
		if (JavaUtil.notNullAndEmpty(speciality)) {
			predicate = cb.and(predicate, cb.like(root.get("doctorInfos").get("speciality"), speciality + "%"));
		}

		userCriteria.where(predicate);
		
		TypedQuery<UserAccount> query = em.createQuery(userCriteria);
		return query.getResultList();
	}
}