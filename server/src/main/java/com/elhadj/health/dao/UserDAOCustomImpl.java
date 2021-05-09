package com.elhadj.health.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.model.User;

@Repository
public class UserDAOCustomImpl implements UserDAOCustom{
	@PersistenceContext
	EntityManager em;

	@Override
	public User loadUserByEmail(String email) throws Exception {
		javax.persistence.Query nq = em.createNativeQuery("select * from User where email=:email", User.class);
		nq.setParameter("email", email);
		List<User> users = nq.getResultList();
		if (users.isEmpty())
			throw new Exception();
		return users.get(0);
	}

	@Override
	@Transactional
	public int updateUserPassword(long userId, String password) {
		Query nq = em.createNativeQuery("update User set password=:password where id=:id", User.class);
		nq.setParameter("password", password);
		nq.setParameter("id", userId);
		int n = nq.executeUpdate();
		return n;
	}

	@Override
	@Transactional
	public void updateUserCredentials(long userId, String newEmail, String newTel) throws Exception {
		Query nq = em.createNativeQuery("update User set email=:email, tel=:tel where id=:id", User.class);
		nq.setParameter("email", newEmail);
		nq.setParameter("tel", newTel);
		nq.setParameter("id", userId);
		if (nq.executeUpdate() == 0) {
			throw new SHRuntimeException(404, "cannot update user", "the path parameter id didn't match any user");
		}
	}
}
