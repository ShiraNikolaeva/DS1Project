package dsFinalProjectMyCopy;

public class CreditCard {

	private String ccNum;
	private int secCode; //secuirty code--3 or 4 digits
	//we dont need expiration? 
	
	
	public CreditCard(String ccNum, int secCode) {
		if(!String.valueOf(secCode).matches("[0-9]{4}") && !String.valueOf(secCode).matches("[0-9]{3}") ) {
			throw new InvalidCodeException();
		}
		if(!ccNum.matches("[0-9]{16}") ) {
			throw new InvalidCardNumException();
		}
		this.ccNum=ccNum;
		this.secCode=secCode;
	}
	
	protected String getCcNum() {
		return ccNum;
	}
	protected int getSecCode() {
		return secCode;
	}
	
}
