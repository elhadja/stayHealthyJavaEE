package com.elhadj.health.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
