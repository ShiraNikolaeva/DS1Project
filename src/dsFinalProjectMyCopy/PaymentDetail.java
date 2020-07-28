package dsFinalProjectMyCopy;

import java.io.Serializable;

public class PaymentDetail implements Serializable {

	private long paymentID;
	private PaymentMethod method;
	private double dollarAmount;
	private String cardID;
	
	public PaymentDetail(Long paymentID, String method, double amount) {
		if(method.equals("CREDITCARD") || method.equals("DEBITCARD") || method.equals("EBTCARD")) {
			throw new MissingCardIdException("Need card number when paying with a card");
		}
		this.paymentID=paymentID;
		this.method=PaymentMethod.valueOf(method);
		this.dollarAmount=amount;
		cardID=null;
	}
	
	public PaymentDetail(Long paymentID, String method, double dollarAmount,String cardID) {
		if(!cardID.matches("[0-9]{16}") ) {
			throw new InvalidCardNumException();
		}
		this.paymentID=paymentID;
		this.method=PaymentMethod.valueOf(method);
		this.dollarAmount=dollarAmount;
		this.cardID=cardID;
	}
	
	//getters
	public long getPaymentID() {
		return paymentID;
	}
	
	public double getAmount() {
		return dollarAmount;
	}
	
	public String getPayMethod() {
		return method.toString();
	}
	
	public String getCardID() {
		return cardID;
	}
	
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder();
		str.append("payment ID: "+paymentID);
		str.append(" payment method: "+method);
		str.append(" amount: "+dollarAmount);
		if(cardID!=null) {
			str.append(" card ID: "+cardID);
		}
		return str.toString();
	}
	
	
}
