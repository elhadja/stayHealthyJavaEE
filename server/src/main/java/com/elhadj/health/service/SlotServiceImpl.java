package com.elhadj.health.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dao.SlotDAO;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.model.Slot;
import com.elhadj.health.model.User;

@Service
public class SlotServiceImpl implements SlotService {
	@Autowired
	SlotDAO slotDAO;
	
	@Autowired
	UserDAO userDAO;

	@Override
	public long addSlot(AddSlotRequestDTO input, long doctorId) throws Exception {
		input.validate();
		User doctor = userDAO.findById(doctorId).get();
		Slot slot = new Slot();
		// TODO undestand why model mapper set wrong id
		slot.setDate(input.getLocalDate()); // TODO use the correct value
		slot.setStartTime(input.getLocaltime());
		slot.setDoctor(doctor);
		slot.setUsed(false);
		try {
			slotDAO.save(slot);
		} catch (Exception e) {
			throw new SHRuntimeException(400, "le creneaux existe déjà", "time and date should be unique");
		}

		return slot.getId();
	}

}
