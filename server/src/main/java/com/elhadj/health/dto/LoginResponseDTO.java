package com.elhadj.health.dto;

public class LoginResponseDTO {
	private String token;
	private long id;
	private int userType;
	
	public LoginResponseDTO(String token, long id, int userType) {
		super();
		this.token = token;
		this.id = id;
		this.userType = userType;
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

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}
}
