package com.elhadj.health.integrationTests;

import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.elhadj.health.Util;
import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dao.SlotDAO;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.model.Slot;
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
	
	@AfterEach public void help() {
		userService.deleteAll();
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
	
	@ParameterizedTest
	@CsvSource({"2021-04-26, 5",
				"2022-04-26, 0",
				"2021-06-28, 1"})
	public void it_should_get_slots_by_doctorId_and_date(String date, String expectedSlots) throws Exception {
		long id = Util.addUser("testspringee@mail.com", SHConstants.DOCTOR, userService);
		addSlot("2021-04-26", "11:00", id);
		addSlot("2021-04-27", "11:00", id);
		addSlot("2021-05-27", "11:00", id);
		addSlot("2021-06-27", "11:00", id);
		addSlot("2021-06-28", "11:00", id);
		
		List<Slot> slots = slotService.getByCriteria(id, date);

		Assert.isTrue(slots.size() == Integer.parseInt(expectedSlots), "");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"2021/04-26", "2021/15/01", "2021/1/32"})
	public void it_should_fail_if_date_are_wrong_format(String date) throws Exception {
		long id = Util.addUser("testspringee@mail.com", SHConstants.DOCTOR, userService);

		try {
			slotService.getByCriteria(id, date);
			fail("");
		} catch (SHRuntimeException e) {
			
		}
	}
	
	private long addSlot(String date, String time, long id) throws Exception {
		AddSlotRequestDTO input = new AddSlotRequestDTO();
		input.setDate(date);
		input.setStartTime(time);
		return slotService.addSlot(input, id);
	}
}
