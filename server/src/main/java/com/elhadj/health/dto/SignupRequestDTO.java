package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.model.Address;

public class SignupRequestDTO implements ValidateDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private AddressDTO address;
	private int userType;
	
	
	public SignupRequestDTO() {
		userType = -1;
	}
	
	public SignupRequestDTO(String firstName, String lastName, String email, String password, int userType) {
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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	
	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Override
	public void validate() {
		if (firstName == null || firstName.isEmpty() 
				|| lastName == null || lastName.isEmpty()
				|| email == null || email.isEmpty()
				|| password == null || password.isEmpty()
				) {
			throw new SHRuntimeException(400, "formulaire invalide","firstName, lastName, email, password and userType are required and cannot be empty");
		}
		
		if (userType == -1) {
			throw new SHRuntimeException(400, "user type is required","user type is required");
		}
		
		if (address != null)
			this.address.validate();
	}
}
