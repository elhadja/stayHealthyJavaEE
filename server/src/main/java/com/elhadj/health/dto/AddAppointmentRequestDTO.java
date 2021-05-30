package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;

public class AddAppointmentRequestDTO implements ValidateDTO {
	private String raison;
	private long slotId;

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}
	
	public long getSlotId() {
		return slotId;
	}

	public void setSlotId(long slotId) {
		this.slotId = slotId;
	}

	@Override
	public void validate() {
		if (slotId <= 0) {
			throw new SHRuntimeException(400, "slot invalide", "the slot id must be greater than 0");
		}
	}
}
