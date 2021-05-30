package com.elhadj.health.service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dao.SlotDAO;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.AddAppointmentRequestDTO;
import com.elhadj.health.model.Appointment;
import com.elhadj.health.model.Slot;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	AppointmentDAO appointmentDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	SlotDAO slotDAO;

	@Override
	@Transactional
	public long addAppointment(AddAppointmentRequestDTO input, long patientId) {
		input.validate();
		Appointment app = new Appointment();
		app.setRaison(input.getRaison());
		try {
			Slot slot = slotDAO.findById(input.getSlotId()).get();
			if (slot.isUsed()) {
				throw new SHRuntimeException(400, "slot déjà utilisé", "slot aready used");
			}
			slot.setUsed(true);
			app.addSlot(slot);
			app.setDoctor(slot.getDoctor());
		} catch (NoSuchElementException e) {
			throw new SHRuntimeException(400, "slot non trouvé", "slot not found");
		}
		
		try {
			app.setPatient(userDAO.findById(patientId).get());
		} catch(NoSuchElementException e) {
			throw new SHRuntimeException(400, "patient non trouvé", "patient not found");
		}
		
		appointmentDAO.save(app);

		return app.getId();
	}
}
