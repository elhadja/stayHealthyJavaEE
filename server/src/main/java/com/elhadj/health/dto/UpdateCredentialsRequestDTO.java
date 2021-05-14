package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;

public class UpdateCredentialsRequestDTO implements ValidateDTO{
	private String email;
	private String tel;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public void validate() {
		if (email==null || email.isEmpty()) {
			throw new SHRuntimeException(400, "formulaire invalide", "email are required");
		}
	}
}
