package com.elhadj.health.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address implements Cloneable {
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="road")
	private String road;
	
	@Column(name="postalCode")
	private int postalCode;

	@Column(name="city")
	private String city;
	
	public Address() {}
	
	public Address(String road, int postalCode, String city) {
		super();
		this.road = road;
		this.postalCode = postalCode;
		this.city = city;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + postalCode;
		result = prime * result + ((road == null) ? 0 : road.hashCode());
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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (id != other.id)
			return false;
		if (postalCode != other.postalCode)
			return false;
		if (road == null) {
			if (other.road != null)
				return false;
		} else if (!road.equals(other.road))
			return false;
		return true;
	}
	
	public Address clone() {
		Address address = null;
		try {
			address = (Address) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return address;
	}
}
