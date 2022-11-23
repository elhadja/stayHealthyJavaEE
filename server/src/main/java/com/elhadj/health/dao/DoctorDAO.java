package com.elhadj.health.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elhadj.health.model.UserAccount;

@Repository
public interface DoctorDAO extends CrudRepository<UserAccount, Long>, DoctorDAOCustom {

}
