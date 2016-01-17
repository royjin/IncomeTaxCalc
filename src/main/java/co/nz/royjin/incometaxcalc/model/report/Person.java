package co.nz.royjin.incometaxcalc.model.report;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 590562254228908464L;
	
	private String firstName;
	
	private String lastName;

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(firstName != null ? firstName : "");
		builder.append(" ");
		builder.append(lastName != null ? lastName : "");
		return builder.toString();
	}
}
