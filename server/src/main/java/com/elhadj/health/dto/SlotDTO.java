package com.elhadj.health.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SlotDTO {
	private long id;
	private String date;
	private String startTime;
	private boolean isUsed;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date.toString();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime.toString();
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
}
