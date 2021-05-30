package com.elhadj.health.integrationTests;

import static org.assertj.core.api.Assertions.fail;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.elhadj.health.Util;
import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dao.SlotDAO;
import com.elhadj.health.dto.AddAppointmentRequestDTO;
import com.elhadj.health.model.Appointment;
import com.elhadj.health.service.AppointmentDAO;
import com.elhadj.health.service.AppointmentService;
import com.elhadj.health.service.SlotService;
import com.elhadj.health.service.UserService;

@SpringBootTest
public class AppointmentServiceITest {
	@Autowired
	AppointmentService appointemntService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	SlotService slotService;
	
	@Autowired
	AppointmentDAO appointmentDAO;
	
	@Autowired
	SlotDAO slotDAO;
	
	@AfterEach
	public void cleanDB() {
		userService.deleteAll();
	}
	
	@Test
	public void it_should_appointement() throws Exception {
		long doctorId = Util.addUser("doctormail@testjee.com", SHConstants.DOCTOR, userService);
		long slotId = Util.addSlot("2021-05-30", "11:25", doctorId, slotService);
		long patientId = Util.addUser("patientmail@testjee.com", SHConstants.PATIENT, userService);
		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		input.setRaison("consultation dentaire");
		input.setSlotId(slotId);
		
		long appId = appointemntService.addAppointment(input, patientId);
		try {
			Appointment appointment = appointmentDAO.findById(appId).get();
			Assert.isTrue(appointment.getRaison().equals(input.getRaison()), "");
			Assert.isTrue(appointment.getPatient() != null, "");
			Assert.isTrue(appointment.getDoctor() != null, "");
			Assert.isTrue(appointment.getSlot() != null, "");
			Assert.isTrue(appointment.getSlot().isUsed(), "");
		} catch(NoSuchElementException e) {
			fail("");
		}
	}
	
	@Test
	public void it_should_fail_if_input_are_invalid() throws Exception {
		Util.addUser("doctormail@testjee.com", SHConstants.DOCTOR, userService);
		long patientId = Util.addUser("patientmail@testjee.com", SHConstants.PATIENT, userService);
		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		input.setRaison("consultation dentaire");
		input.setSlotId(1);
		
		try {
			appointemntService.addAppointment(input, patientId);
			fail("");
		} catch(SHRuntimeException e) {

		}
	}
	
	@Test
	public void it_should_fail_if_the_slot_is_used_by_another_appointment() throws Exception {
		long doctorId = Util.addUser("doctormail@testjee.com", SHConstants.DOCTOR, userService);
		long slotId = Util.addSlot("2021-05-30", "11:25", doctorId, slotService);
		long patientId = Util.addUser("patientmail@testjee.com", SHConstants.PATIENT, userService);
		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		input.setRaison("consultation dentaire");
		input.setSlotId(slotId);
		
		appointemntService.addAppointment(input, patientId);
		try {
			appointemntService.addAppointment(input, patientId);
			fail("");
		}catch(SHRuntimeException e) {

		}
	}
	
	@Test
	public void it_should_cancel_appointment() throws Exception {
		long doctorId = Util.addUser("doctormail@testjee.com", SHConstants.DOCTOR, userService);
		long slotId = Util.addSlot("2021-05-30", "11:25", doctorId, slotService);
		long patientId = Util.addUser("patientmail@testjee.com", SHConstants.PATIENT, userService);
		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		input.setRaison("consultation dentaire");
		input.setSlotId(slotId);
		long appointmentId = appointemntService.addAppointment(input, patientId);
		
		appointemntService.cancelAppointment(appointmentId);
		
		try {
			appointmentDAO.findById(appointmentId).get();
			fail("appointment should be deleted");
		} catch (NoSuchElementException e) {
			Assert.isTrue(!slotDAO.findById(slotId).get().isUsed(), "slot should be free");
		}
	}
}
