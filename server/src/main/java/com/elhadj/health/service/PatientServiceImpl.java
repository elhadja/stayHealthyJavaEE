package com.elhadj.health.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.PatientDTO;
import com.elhadj.health.dto.UpdatePatientRequestDTO;
import com.elhadj.health.dto.UserDTO;
import com.elhadj.health.model.Address;
import com.elhadj.health.model.User;
import com.elhadj.health.util.JavaUtil;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	UserDAO userDao;

	public PatientDTO getById(long id) {
		User user = null;
		try {
			user =  (User) userDao.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new SHRuntimeException(404, "utilisateur non trouvé", "no user match the path paramte id");
		}

		PatientDTO dto = JavaUtil.convertTo(user, PatientDTO.class);
		dto.setPassword(null);
		return dto;
	}

	@Override
	public UpdatePatientRequestDTO update(UpdatePatientRequestDTO dto, long userId) throws Exception {
		User user = null;
		try {
			user =  (User) userDao.findById(userId).get();
		} catch (NoSuchElementException e) {
			throw new SHRuntimeException(404, "utilisateur non trouvé", "no user match the path paramte id");
		}

		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		if (dto.getAddress() != null) {
			dto.getAddress().validate();
			user.setAddress(JavaUtil.convertTo(dto.getAddress(), Address.class));
		}
		userDao.save(user);

		return JavaUtil.convertTo(user, UpdatePatientRequestDTO.class);
	}
	
	
}
