package com.elhadj.health.dto;

public class CreateRessouceResponseDTO {
	private int stausCode;
	private long id;

	public CreateRessouceResponseDTO(int stausCode, long id) {
		this.stausCode = stausCode;
		this.id = id;
	}

	public int getStausCode() {
		return stausCode;
	}

	public void setStausCode(int stausCode) {
		this.stausCode = stausCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
