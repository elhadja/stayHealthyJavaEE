package com.elhadj.health.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.elhadj.health.Exception.SHRuntimeException;

public class AddSlotRequestDTO implements ValidateDTO {
	private String date;
	private String startTime;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public LocalDate getLocalDate() {
		return LocalDate.parse(date);
	}
	
	public LocalTime getLocaltime() {
		return LocalTime.parse(startTime);
	}

	@Override
	public void validate() {
		if (date == null || date.toString().isBlank() || startTime == null || date.toString().isBlank()) {
			throw new SHRuntimeException(400, "l'heure de debut et la date sont obligatoire et ne peuvent pas être vides",
												"date and starttime are required are required and connot be empty");
		}
	}
}
