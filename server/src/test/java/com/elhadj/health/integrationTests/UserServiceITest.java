package com.elhadj.health.integrationTests;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.elhadj.health.Util;
import com.elhadj.health.common.SHConstants;
import com.elhadj.health.dto.AddAppointmentRequestDTO;
import com.elhadj.health.dto.GetAppointmentDTOResponse;
import com.elhadj.health.service.AppointmentService;
import com.elhadj.health.service.SlotService;
import com.elhadj.health.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceITest {
	@Autowired
	private SlotService slotService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@AfterEach
	public void cleanDB() {
		userService.deleteAll();
	}

	@ParameterizedTest
	@CsvSource({"null, null, 5", "2021-05-22, 2021-05-24, 3", "2021-05-21, null, 4", "null , 2021-05-21, 2"})
	public void it_should_get_all_userAppointments(String startDate, String endDate, int numberExpectedAppointments) throws Exception {
		//given
		if (startDate.equals("null"))
			startDate = null;
		if (endDate.equals("null"))
			endDate = null;

		long doctorId = Util.addUser("testpatient@testyyy.jeespring", SHConstants.DOCTOR, userService);
		long slot1 = Util.addSlot("2021-05-20", "11:00", doctorId, slotService);
		long slot2 = Util.addSlot("2021-05-21", "11:00", doctorId, slotService);
		long slot3 = Util.addSlot("2021-05-22", "11:00", doctorId, slotService);
		long slot4 = Util.addSlot("2021-05-23", "11:00", doctorId, slotService);
		long slot5 = Util.addSlot("2021-05-24", "11:00", doctorId, slotService);

		AddAppointmentRequestDTO input = new AddAppointmentRequestDTO();
		input.setRaison("contrôle dentaire");
		input.setSlotId(slot1);
		long patientId = Util.addUser("testxxx@doctor.jeespring", SHConstants.PATIENT, userService);
		appointmentService.addAppointment(input, patientId);
		input.setSlotId(slot2);
		appointmentService.addAppointment(input, patientId);
		input.setSlotId(slot3);
		appointmentService.addAppointment(input, patientId);
		input.setSlotId(slot4);
		appointmentService.addAppointment(input, patientId);
		input.setSlotId(slot5);
		appointmentService.addAppointment(input, patientId);


		//when
		List<GetAppointmentDTOResponse> patientAppoitments = userService.getUserAppointments(patientId, startDate, endDate);
		List<GetAppointmentDTOResponse> doctorAppoitments = userService.getUserAppointments(doctorId, startDate, endDate);
		
		//then
		Assert.isTrue(patientAppoitments.size() == numberExpectedAppointments, patientAppoitments.size() + " should be " + numberExpectedAppointments);
		Assert.isTrue(patientAppoitments.size() == doctorAppoitments.size(), "should be equals");
	}
}
