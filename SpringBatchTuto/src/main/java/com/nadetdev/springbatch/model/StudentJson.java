package com.nadetdev.springbatch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentJson {
	
	private Long id;
	
	@JsonProperty("first_name")
	private String firstName;
	
	
	//private String lastName;
	
	private String email;
	
	
	public StudentJson() {
		super();
	}


	public StudentJson(Long id, String firstName,  String email) {
		super();
		this.id = id;
		this.firstName = firstName;
	
		this.email = email;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


//	public String getLastName() {
//		return lastName;
//	}
//
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StudentJson [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		//builder.append(lastName);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}

}
