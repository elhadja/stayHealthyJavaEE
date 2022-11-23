package com.elhadj.health.dao;

import com.elhadj.health.model.UserAccount;

public interface UserDAOCustom {
	public UserAccount loadUserByEmail(String email) throws Exception;
	public int updateUserPassword(long userId, String password);
	public void updateUserCredentials(long userId, String newEmail, String newTel) throws Exception;
}
