package com.justgivemethenumber.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ticker {
	
	@Id
	private String name;
	
	public Ticker() {}

	public Ticker(String name) {
		super();
		this.name = name;;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticker other = (Ticker) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Ticker [name=" + name + "]";
	}

}
