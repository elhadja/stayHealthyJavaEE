package com.elhadj.health.dao;

import com.elhadj.health.model.User;

public interface UserDAOCustom {
	public User loadUserByEmail(String email) throws Exception;
	public int updateUserPassword(long userId, String password);
	public void updateUserCredentials(long userId, String newEmail, String newTel) throws Exception;
}
