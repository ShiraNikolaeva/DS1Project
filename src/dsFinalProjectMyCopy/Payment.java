package dsFinalProjectMyCopy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Payment implements Serializable{

	private static long lastPaymentID = 0;
	private long paymentID;
	private long orderID;
	private LocalDate datePaid;
	private ArrayList<PaymentDetail> paymentDetails;
	private PaymentType paymentType;
	private double totalPaid;
	
	public Payment(Long orderID, PaymentType paymentType) {		
		this.paymentType=paymentType;
		this.orderID=orderID; //no way to really validate in constructor, will validate before calling this constructor
		lastPaymentID++;	
		this.paymentID=lastPaymentID;
		this.datePaid=LocalDate.now();
		totalPaid=0;
	}
	//cant make copy constructor bec of ID, is this an issue?
	
	public void addDetail (double amount, String method) {
		totalPaid+=amount;
		PaymentDetail pd=new PaymentDetail(paymentID, method, amount);
		paymentDetails.add(pd);
	}
	

	public void addDetail(double amount, String method,String cardID) {	
		totalPaid+=amount;
		PaymentDetail pd=new PaymentDetail(paymentID, method, amount, cardID);
		paymentDetails.add(pd);		
	}
	
	public void addDetail(PaymentDetail detail) {
		totalPaid+=detail.getAmount();
		paymentDetails.add(detail);		
	}
	
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder();
		str.append("payment id: "+paymentID);
		str.append(" order id: "+orderID);
		str.append(" date paid: "+datePaid );
		str.append(" payment type: "+paymentType);
		str.append(" total paid: "+totalPaid);
		for(PaymentDetail p: paymentDetails) {
			str.append("\n "+ p.toString());
		}
		return str.toString();
	}
	
	//getters
	public long getPaymentID() {
		return paymentID;
	}	
	public long getOrderID() {
		return orderID;
	}	
	public LocalDate getDatePaid() {
		LocalDate paid=LocalDate.of(datePaid.getYear(), datePaid.getMonthValue(), datePaid.getDayOfMonth());
		return paid;
	}
	public String getPaymentType() {
		return paymentType.toString();
	}
	public double getTotalPaid() {
		return totalPaid;
	}
	protected ArrayList<PaymentDetail> getPaymentDetails() {
		return paymentDetails;  
	}
	
	
	 
	
	
}
