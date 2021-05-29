package com.elhadj.health.service;

import java.util.List;

import com.elhadj.health.dto.AddSlotRequestDTO;
import com.elhadj.health.model.Slot;

public interface SlotService {
	long addSlot(AddSlotRequestDTO input, long doctorId) throws Exception;
	List<Slot> getByCriteria(long doctorId, String date) throws Exception;
}