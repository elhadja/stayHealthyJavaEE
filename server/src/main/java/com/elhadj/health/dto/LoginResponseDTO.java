package com.elhadj.health.dto;

public class LoginResponseDTO {
	private String token;
	private long id;
	
	public LoginResponseDTO(String token, long id) {
		super();
		this.token = token;
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
