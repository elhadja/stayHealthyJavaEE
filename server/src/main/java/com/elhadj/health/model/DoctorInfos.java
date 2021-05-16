package com.elhadj.health.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity(name = "doctorInfos")
public class DoctorInfos {
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	public String presentation;
	public String speciality;

	@ElementCollection(fetch = FetchType.EAGER)
	public Set<String> meanOfPayment;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<Diplomas> diplomas;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	public List<Price> prices;
	
	public DoctorInfos() {
		this.meanOfPayment = new HashSet<String>();
		this.diplomas = new ArrayList<Diplomas>();
		this.prices = new ArrayList<Price>();
	}

	public DoctorInfos(@NotNull String firstName, @NotNull String lastName, @NotNull String email,
			@NotNull String password) {
		this.meanOfPayment = new HashSet<String>();
		this.diplomas = new ArrayList<Diplomas>();
		this.prices = new ArrayList<>();
	}
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}