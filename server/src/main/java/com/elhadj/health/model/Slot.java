package com.elhadj.health.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(
   uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "startTime", "doctorId"})}
)
@Entity(name="Slot")
public class Slot {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne
	@JoinColumn(name="doctorId", foreignKey = @ForeignKey(name = "doctorId_FK"))
	private User doctor;
	
	@OneToOne
	@JoinColumn(name="appointmentId", nullable = true)
	private Appointment appointment;
	
	private java.time.LocalDate date;
	
	private java.time.LocalTime startTime;
	
	private boolean isUsed;
	
	public Slot() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getDoctor() {
		return doctor;
	}

	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}

	public java.time.LocalDate getDate() {
		return date;
	}

	public void setDate(java.time.LocalDate date) {
		this.date = date;
	}

	public java.time.LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(java.time.LocalTime startTime) {
		this.startTime = startTime;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Slot other = (Slot) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
