package com.elhadj.health.dto;

import java.util.List;
import java.util.Set;

import com.elhadj.health.model.Diplomas;
import com.elhadj.health.model.Price;

public class DoctorInfosDTO {
	private String presentation;
	private String speciality;
	private Set<String> meanOfPayment;
	private List<Diplomas> diplomas;
	private List<Price> prices;

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}

	public String getSpeciality() {
		return speciality;
	}
	
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public Set<String> getMeanOfPayment() {
		return meanOfPayment;
	}

	public void setMeanOfPayment(Set<String> meanOfPayment) {
		this.meanOfPayment = meanOfPayment;
	}

	public List<Diplomas> getDiplomas() {
		return diplomas;
	}

	public void setDiplomas(List<Diplomas> diplomas) {
		this.diplomas = diplomas;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
}
