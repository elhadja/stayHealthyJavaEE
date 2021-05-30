package com.elhadj.health.service;

import com.elhadj.health.dto.AddAppointmentRequestDTO;

public interface AppointmentService {
	long addAppointment(AddAppointmentRequestDTO input, long patientId);
	void cancelAppointment(long appointmentId);
}
