package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.model.Address;

public class SignupRequestDTO implements ValidateDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Address address;
	private String userType;
	
	
	public SignupRequestDTO() {
	}
	
	public SignupRequestDTO(String firstName, String lastName, String email, String password, String userType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public void validate() {
		if (firstName == null || firstName.isEmpty() 
				|| lastName == null || lastName.isEmpty()
				|| email == null || email.isEmpty()
				|| password == null || password.isEmpty()
				|| userType == null || userType.isEmpty()) {
			throw new SHRuntimeException(400, "formulaire invalide","firstName, lastName, email, password and userType are required and cannot be empty");
		}
	}
}
