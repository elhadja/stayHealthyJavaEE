package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.model.Address;

public class SignupRequestDTO implements ValidateDTO {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private AddressDTO address;
	private boolean isPatient;
	
	
	public SignupRequestDTO() {
	}
	
	public SignupRequestDTO(String firstName, String lastName, String email, String password, boolean userType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.isPatient = userType;
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

	public boolean getUserType() {
		return isPatient;
	}

	public void setUserType(boolean userType) {
		this.isPatient = userType;
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
		if (address != null)
			this.address.validate();
	}
}
