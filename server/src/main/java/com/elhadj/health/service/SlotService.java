package com.elhadj.health.service;

import java.util.List;

import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.dto.SlotDTO;
import com.elhadj.health.dto.UpdateSlotDTORequest;
import com.elhadj.health.model.Slot;

public interface SlotService {
	long addSlot(AddSlotRequestDTO input, long doctorId) throws Exception;
	List<SlotDTO> getByCriteria(long doctorId, String date) throws Exception;
	void update(long id, UpdateSlotDTORequest input);
}