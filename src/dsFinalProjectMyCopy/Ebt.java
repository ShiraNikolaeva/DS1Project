package dsFinalProjectMyCopy;

public class Ebt {
	
	private String cardNum;
	private int pin;
	private double currentBalance; //does not rollover, this amount is refreshed each month. for our program right now it is all run within the same 1 month
	
	public Ebt(String cardNum, int pin, double currentBalance) {
		if(!String.valueOf(pin).matches("[0-9]{4}")) {
			throw new InvalidPinException();
		}
		if(!cardNum.matches("[0-9]{16}")) {
			throw new InvalidCardNumException();
		}
		this.pin=pin;
		this.cardNum=cardNum;
		this.currentBalance=currentBalance;
	}

	public String getCardNum() {
		return cardNum;
	}

	public int getPin() {
		return pin;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBlance) { //this is to simulate the government resetting the value to their allowance each month
		this.currentBalance = currentBlance;
	}
	
	public void decrementCurrBalance(double val) { //this is for the store when customer uses this card to make a payment, ofc after verifying that val is <=currBalance
		currentBalance-=val;
	}
	
	

}
