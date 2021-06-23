package com.elhadj.health.dto;

import com.elhadj.health.model.DoctorInfos;

public class GetAppointmentDTOResponse {
	private String raison;
	private AddSlotRequestDTO slot;
	private UserDTO user;
	private DoctorInfosDTO doctorInfos;

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public AddSlotRequestDTO getSlot() {
		return slot;
	}

	public void setSlot(AddSlotRequestDTO slot) {
		this.slot = slot;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public DoctorInfosDTO getDoctorInfos() {
		return doctorInfos;
	}

	public void setDoctorInfos(DoctorInfosDTO doctorInfos) {
		this.doctorInfos = doctorInfos;
	}
}
