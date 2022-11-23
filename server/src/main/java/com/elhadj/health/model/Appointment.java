package com.elhadj.health.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name="Appointment")
public class Appointment {
	@Id
	@GeneratedValue
	private long id;

	private String raison;
	
	@OneToOne(mappedBy = "appointment", cascade = {CascadeType.PERSIST})
	private Slot slot;

	@ManyToOne
	@JoinColumn(name="patientId")
	private UserAccount patient;
	
	@ManyToOne
	@JoinColumn(name="doctorId")
	private UserAccount doctor;
	
	public Appointment() {}
	
	public void addSlot(Slot slot) {
		this.slot = slot;
		this.slot.setAppointment(this);
	}
	
	public void feeSlot() {
		if (this.slot != null) {
			this.slot.setAppointment(null);
			this.slot = null;
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRaison() {
		return raison;
	}

	public void setRaison(String raison) {
		this.raison = raison;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public UserAccount getPatient() {
		return patient;
	}

	public void setPatient(UserAccount patient) {
		this.patient = patient;
	}

	public UserAccount getDoctor() {
		return doctor;
	}

	public void setDoctor(UserAccount doctor) {
		this.doctor = doctor;
	}
}
