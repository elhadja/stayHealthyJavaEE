package com.elhadj.health.dto;

import com.elhadj.health.model.Address;

// TODO Fix user, doctor, patient DTOs
public class UserDTO {
	private long id;
	private String firstName;
	private String lastName;
	private String tel;
	private String email;
	private String password; // TODO to remove
	private Address address; 
	private boolean userType;
	
	public UserDTO() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public boolean getUserType() {
		return userType;
	}

	public void setUserType(boolean userType) {
		this.userType = userType;
	}
}
