package dsFinalProjectMyCopy;

public class debitCard {
	private String dcNum;
	private int pin; //4 digits
	//we dont need expiration? 
	
	
	public debitCard(String dcNum, int pin) {
		if(!String.valueOf(pin).matches("[0-9]{4}")) {
			throw new InvalidPinException();
		}
		if(!dcNum.matches("[0-9]{16}")) {
			throw new InvalidCardNumException();
		}
		this.dcNum=dcNum;
		this.pin=pin;
	}
	
	protected String getDcNum() {
		return dcNum;
	}
	protected int getPin() {
		return pin;
	}
	
}
