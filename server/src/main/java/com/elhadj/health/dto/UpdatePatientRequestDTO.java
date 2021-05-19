package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.util.JavaUtil;

public class UpdatePatientRequestDTO implements ValidateDTO {
	private String firstName;
	private String lastName;
	private AddressDTO address;

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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	@Override
	public void validate() {
		if (!JavaUtil.notNullAndEmpty(firstName) || !JavaUtil.notNullAndEmpty(lastName)) {
			throw new SHRuntimeException(400, "firstname and lastname are required", "required fields");
		}
		
		if (address != null)
			address.validate();
	} 
}
