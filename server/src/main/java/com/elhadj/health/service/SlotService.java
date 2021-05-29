package com.elhadj.health.service;

import com.elhadj.health.dto.AddSlotRequestDTO;

public interface SlotService {
	long addSlot(AddSlotRequestDTO input, long doctorId) throws Exception;
}
