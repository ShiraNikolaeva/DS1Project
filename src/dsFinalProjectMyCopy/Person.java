
package dsFinalProjectMyCopy;
/*
 * 
 

* Made by Shira Nikolaeva
 * Date: 12/15/2019
*/

import java.io.Serializable;

public class Person implements Comparable<Person>,Serializable {

	private String firstName, lastName, phoneNumber;
	private Address address;
	private char gender;
	private Long id;
	private static long lastID = 0;

	public Person(String fname, String lname, String num, Address ad, char gender) {
		if(!validateGender(gender)) {
			throw new InvalidDataException();
		}
		setFirstName(fname);
		setLastName(lname);
		setPhoneNumber(num);
		this.gender=gender;
		this.id = lastID;
		lastID++;
	}

	public void setFirstName(String fname) {
		if (fname == null) {
			throw new NullPointerException();
		}
		this.firstName = fname;
	}

	public void setLastName(String lname) {
		if (lname == null) {
			throw new NullPointerException();
		}
		this.lastName = lname;
	}

	public void setPhoneNumber(String num) {
		if (num == null) {
			throw new NullPointerException();
		}
		if (!validatePhoneNum(num)) {
			throw new InvalidNumberException();
		}
		this.phoneNumber = num;
	}

	private boolean validatePhoneNum(String num) {
		//sn validate that its 10 digits
		if (num.matches("[0-9]+") && num.length() == 10) {
			return true;
		}
		return false;
	}

	private boolean validateGender(char gender) {
		//sn allow only F or M
		//sn called after checking that gender is not 0
		if(gender=='F'||gender=='M') {
			return true;
		}
		return false;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public Address getAdress() {
		return this.address.clone();
	}

	public char getGender() {
		return this.gender;
	}

	public Long getID() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ID: " + id + " Name: " + firstName + " " + lastName + " Gender: " + gender + " Phone Number: "
				+ phoneNumber + " Address: " + address.toString();
	}

	@Override
	public boolean equals(Object o) {
		// sn objects are equal if their id's are equal
		if (o == null || this.getClass() != o.getClass() || this.id != ((Person) o).getID()) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Person p) {
		return this.id.compareTo(p.getID());
	}
}
