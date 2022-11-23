package com.elhadj.health.unitTests.services;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.DoctorDTO;
import com.elhadj.health.model.Address;
import com.elhadj.health.model.Diplomas;
import com.elhadj.health.model.DoctorInfos;
import com.elhadj.health.model.UserAccount;
import com.elhadj.health.service.DoctorService;

@SpringBootTest
public class DoctorServiceTest {
	
	@Autowired
	DoctorService doctorService;
	
	@MockBean
	UserDAO userDAO;
	
	@Test
	public void it_should_get_a_doctor_by_id_with_all_fields() throws Exception {
		UserAccount user = new UserAccount();
		user.setId(111111);
		user.setFirstName("elhadj");
		user.setLastName("bah");
		user.setTel("0000000");
		Address address = new Address();
		address.setRoad("road of success");
		address.setCity("success city");
		address.setPostalCode(33400);
		user.setAddress(address);
		user.setUserType(SHConstants.DOCTOR);
		DoctorInfos di = new DoctorInfos();
		di.setPresentation("presentation");
		di.setDiplomas(Arrays.asList(new Diplomas("d1", "univ1")));
		user.setDoctorInfos(di);
		when(userDAO.findById(1L)).thenReturn(Optional.of(user));
		
		DoctorDTO dto = doctorService.getById(1);

		Assert.isTrue(dto.getId() == user.getId());
		Assert.isTrue(dto.getAddress().getRoad().equals(user.getAddress().getRoad()));
		Assert.isTrue(dto.getPresentation().equals(user.getDoctorInfos().getPresentation()));
		Assert.isTrue(dto.getDiplomas().equals(user.getDoctorInfos().getDiplomas()));
		Assert.isTrue(dto.getPassword() == null);
	}

}
