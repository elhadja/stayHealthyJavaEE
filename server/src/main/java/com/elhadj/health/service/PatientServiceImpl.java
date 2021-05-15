package com.elhadj.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.PatientDTO;
import com.elhadj.health.dto.UserDTO;
import com.elhadj.health.model.User;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	UserDAO userDao;

	public PatientDTO getById(long id) {
		User user =  (User) userDao.findById(id).get();
		if (user == null) {
			throw new SHRuntimeException(404, "utilisateur non trouvé", "no user match the path paramte id");
		}
		PatientDTO dto = new PatientDTO();
		// TODO do the mapping
		return dto;
	}
}
