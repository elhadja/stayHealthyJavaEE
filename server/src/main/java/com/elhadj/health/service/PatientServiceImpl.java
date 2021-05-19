package com.elhadj.health.service;

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
		User user =  (User) userDao.findById(id).get();
		if (user == null) {
			throw new SHRuntimeException(404, "utilisateur non trouvé", "no user match the path paramte id");
		}

		PatientDTO dto = new PatientDTO();
		return dto;
	}

	@Override
	public UpdatePatientRequestDTO update(UpdatePatientRequestDTO dto, long userId) throws Exception {
		User user = userDao.findById(userId).get();
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
