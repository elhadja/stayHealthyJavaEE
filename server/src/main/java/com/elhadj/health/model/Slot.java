package com.elhadj.health.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((doctor == null) ? 0 : doctor.hashCode());
		result = prime * result + (isUsed ? 1231 : 1237);
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (doctor == null) {
			if (other.doctor != null)
				return false;
		} else if (!doctor.equals(other.doctor))
			return false;
		if (isUsed != other.isUsed)
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
}
