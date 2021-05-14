package com.elhadj.health.dto;

public class UpdatePasswordRequestDTO {
	String password;
	String newPassword;
	
	public UpdatePasswordRequestDTO() {
		
	}
	
	public UpdatePasswordRequestDTO(String password, String newPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
