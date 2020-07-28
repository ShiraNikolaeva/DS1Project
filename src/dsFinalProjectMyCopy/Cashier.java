package dsFinalProjectMyCopy;
/*
 * Made by Shira Nikolaeva
 * Date: 12/15/2019
*/

import java.io.Serializable;

public class Cashier extends Person implements Serializable {

	public Cashier(String fname, String lname, String num, Address ad, char gender) {
		super(fname, lname, num, ad, gender);
	}

	public void setFirstName(String fname) {
		super.setFirstName(fname);
	}

	public void setLastName(String lname) {
		super.setLastName(lname);
	}

	public void setPhoneNumber(String num) {
		super.setPhoneNumber(num);
	}

	public String getFirstName() {
		return super.getFirstName();
	}

	public String getLastName() {
		return super.getLastName();
	}

	public String getPhoneNumber() {
		return super.getPhoneNumber();
	}

	public Address getAdress() {
		return super.getAdress();
	}

	public char getGender() {
		return super.getGender();
	}

	public Long getID() {
		return super.getID();
	}
}
