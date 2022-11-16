package com.elhadj.health.integrationTests;

import static org.assertj.core.api.Assertions.fail;

import java.time.LocalDate;
import java.time.LocalTime;
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
import com.elhadj.health.dto.SlotDTO;
import com.elhadj.health.dto.UpdateSlotDTORequest;
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
		Util.addSlot("2021-04-26", "11:00", id, slotService);
		Util.addSlot("2021-04-27", "11:00", id, slotService);
		Util.addSlot("2021-05-27", "11:00", id, slotService);
		Util.addSlot("2021-06-27", "11:00", id, slotService);
		Util.addSlot("2021-06-28", "11:00", id, slotService);
		
		List<SlotDTO> slots = slotService.getByCriteria(id, date);

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
	
	@Test
	public void it_should_update_the_slot() throws Exception {
		long id = Util.addUser("testspringee@mail.com", SHConstants.DOCTOR, userService);
		long slotId = Util.addSlot("2021-04-26", "11:00", id, slotService);
		UpdateSlotDTORequest input = new UpdateSlotDTORequest();
		input.setDate("2022-06-20");
		input.setStartTime("04:00");
		input.setIsUsed(true);
		
		slotService.update(slotId, input);
		
		Slot slot = slotDAO.findById(slotId).get();
		Assert.isTrue(slot.getDate().equals(LocalDate.parse("2022-06-20")), "");
		Assert.isTrue(slot.getStartTime().equals(LocalTime.parse("04:00")), "");
		Assert.isTrue(slot.isUsed() == true, "");
	}
	
	@Test
	public void it_should_fail() throws Exception {
		long id = Util.addUser("testspringee@mail.com", SHConstants.DOCTOR, userService);
		UpdateSlotDTORequest input = new UpdateSlotDTORequest();
		input.setDate("2021-04-26");
		input.setStartTime("11:00");
		input.setIsUsed(true);
		
		try {
			slotService.update(0, input);
			fail("");
		} catch (SHRuntimeException e) {
			Assert.isTrue(e.getStatusCode() == 404, "");
		}
	}
	
}
