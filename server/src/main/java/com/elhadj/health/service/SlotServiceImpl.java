package com.elhadj.health.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.dao.SlotDAO;
import com.elhadj.health.dao.UserDAO;
import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.dto.SlotDTO;
import com.elhadj.health.dto.UpdateSlotDTORequest;
import com.elhadj.health.model.Slot;
import com.elhadj.health.model.User;
import com.elhadj.health.util.JavaUtil;

import javassist.NotFoundException;

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

	@Override
	public List<SlotDTO> getByCriteria(long doctorId, String date) throws Exception {
		LocalDate _date = null;
		try {
			_date = LocalDate.parse(date);
		} catch (Exception e) {
			throw new SHRuntimeException(400, "date invalide", "date format should be yyyy-mm-dd");
		}
		List<SlotDTO> dtos = new ArrayList<>();
		for (Slot slot : slotDAO.findByDoctorIdAndDateGreaterThanEqual(doctorId, _date)) {
			SlotDTO dto = JavaUtil.convertTo(slot, SlotDTO.class);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void update(long id, UpdateSlotDTORequest input) {
		input.validate();
		Slot slot = null;
		try {
			slot = slotDAO.findById(id).get();
		} catch(NoSuchElementException e) {
			throw new SHRuntimeException(404, "slot non trouvé", "not found");
		}
		slot.setDate(input.getLocalDate());
		slot.setStartTime(input.getLocaltime());
		slot.setUsed(input.getIsUsed());
		
		try {
			slotDAO.save(slot);
		} catch (Exception e) {
			throw new SHRuntimeException(400, "le creneaux existe déjà", "time and date should be unique");
		}
	}
}
