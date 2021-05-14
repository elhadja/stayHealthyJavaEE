package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;

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
	
	public void isValid() throws SHRuntimeException {
		if (password == null || newPassword == null || newPassword.isEmpty()) {
			throw new SHRuntimeException(400, "formulaire invalide", "all fields are required and cannot be empty");
		}
	}
}
