package com.nadetdev.springbatch.model;

public class StudentResponse {
	
	private long id;
	private String lastName;
	private String firstName;
	private String email;

	public StudentResponse() {
		super();
	}

	public StudentResponse(String lastName, String firstName, String email) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
	}

	public StudentResponse(long id, String lastName, String firstName, String email) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StudentResponse [id=");
		builder.append(id);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}

}
