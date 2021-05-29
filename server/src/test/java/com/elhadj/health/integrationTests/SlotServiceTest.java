package com.elhadj.health.integrationTests;

import static org.assertj.core.api.Assertions.fail;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.elhadj.health.Util;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dao.SlotDAO;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.service.SlotService;
import com.elhadj.health.service.UserService;

@SpringBootTest
public class SlotServiceTest {
	@Autowired
	SlotService slotService;

	@Autowired
	UserService userService;
	
	@Autowired
	SlotDAO slotDAO;
	
	@AfterEach
	public void cleanDB() {
		userService.deleteAll();
		slotDAO.deleteAll();
	}
	
	@Test
	public void add_slot_should_succed() throws Exception {
		long id = Util.addUser("testspringee@mail.com", SHConstants.DOCTOR, userService);
		AddSlotRequestDTO input = new AddSlotRequestDTO();
		input.setDate("2021-05-26");
		input.setStartTime("19:57");
		
		long slotId = slotService.addSlot(input, id);
		
		Assert.isTrue(slotId > 0);
	}
	
	@Test
	public void add_slot_should_fail_if_date_and_time_are_not_unique() throws Exception {
		long id = Util.addUser("testspringee@mail.com", SHConstants.DOCTOR, userService);
		AddSlotRequestDTO input = new AddSlotRequestDTO();
		input.setDate("2021-05-26");
		input.setStartTime("19:57");
		slotService.addSlot(input, id);
		
		try {
			slotService.addSlot(input, id);
			fail("slot time and date should be unique");
		} catch (Exception e) {
			
		}
	}
}
