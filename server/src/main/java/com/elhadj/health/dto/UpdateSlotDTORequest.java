package com.elhadj.health.dto;

public class UpdateSlotDTORequest extends AddSlotRequestDTO {
	private boolean isUsed;

	public boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
}
