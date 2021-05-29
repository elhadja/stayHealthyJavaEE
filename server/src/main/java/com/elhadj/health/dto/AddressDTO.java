package com.elhadj.health.dto;

import com.elhadj.health.Exception.SHRuntimeException;
import com.elhadj.health.util.JavaUtil;

public class AddressDTO implements ValidateDTO {
	private String road;
	private int postalCode;
	private String city;

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public void validate() throws SHRuntimeException{
		if (!JavaUtil.notNullAndEmpty(road) || !JavaUtil.notNullAndEmpty(city)) {
			throw new SHRuntimeException(400, "road and city are required and cannot be empty", "required fields");
		}
		if (postalCode <= 0) {
			throw new SHRuntimeException(400, "invalide code paostale", "postal code must greater than 0");
		}
	}
}
