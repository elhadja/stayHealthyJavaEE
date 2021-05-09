package com.elhadj.health.dao;

import com.elhadj.health.model.User;

public interface UserDAOCustom {
	public User loadUserByEmail(String email) throws Exception;
}
