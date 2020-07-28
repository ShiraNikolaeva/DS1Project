package dsFinalProjectMyCopy;
/*
 * Made by Shira Nikolaeva
 * Date: 12/15/2019
*/

import java.io.Serializable;

public class Address implements Comparable<Address>,Serializable{

	private String street, city, zipCode;
	private USState State;

	public Address(String street, String city, String state, String zipCode) {
		if(!validateZipCode(zipCode)) {
			throw new InvalidNumberException();
		}
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		USState State = USState.valueOf(state.toUpperCase());
		this.State = State;
	}

	private boolean validateZipCode(String num) {
		//sn allow only 5 digit zip code
		if (num.matches("[0-9]+") && num.length() == 5) {
			return true;
		}
		return false;	
	}

	public Address(Address a) {
		this.street = a.street;
		this.city = a.city;
		this.State = a.State;
		this.zipCode = a.zipCode;
	}

	public String getStreet() {
		String copy=this.street;
		return copy;
	}

	public String getCity() {
		String copy=this.city;
		return copy;
	}

	public String getZipCode() {
		String copy=this.zipCode;
		return copy;
	}

	public USState getState() {
		return this.State;
	}

	//sn compares based on state,city,street
	public boolean equals(Address a) {
		if(a==null||this.State!=a.State||!this.city.equals(a.city)||!this.street.equals(a.street)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "Address: "+this.street +" "+ this.city +" "+ this.State +" "+ this.zipCode;
	}
	
	public Address clone() {
		String cCity=this.city;
		String cStreet=this.street;
		String cZipcode=this.zipCode;
		String cState=this.State.toString();
		Address copy=new Address(cStreet,cCity,cState,cZipcode);
		return copy;
	}

	@Override
	public int compareTo(Address ad) {
		return this.compareTo(ad);
	}




}
