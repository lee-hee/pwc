package com.pwc.assignment.dto;

import java.io.Serializable;
import java.util.List;

public class ContactDTO implements Serializable,Comparable<ContactDTO>{

	private static final long serialVersionUID = 2673858195573582574L;
	
	private String name;
	private List<String> phoneNumbers;

	public ContactDTO() {
		super();
	}

	public ContactDTO(String name, List<String> phoneNumbers) {
		super();
		this.name = name;
		this.phoneNumbers = phoneNumbers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	@Override
	public int compareTo(ContactDTO otherDto) {
		return this.name.compareTo(otherDto.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ContactDTO other = (ContactDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
