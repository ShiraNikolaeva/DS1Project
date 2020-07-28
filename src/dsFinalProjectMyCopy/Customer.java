package dsFinalProjectMyCopy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Customer extends Person implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CreditCard cc;
	private debitCard dc;
	private Ebt ebtCard;
	private double balance;
	private Stack<SalesOrder>lastPurchases=new Stack<>();
	private ArrayList<SalesOrder>allPurchases=new ArrayList<>(); //using arryList instead of hashMap
	private ArrayList<Payment>payments=new ArrayList<>();

	public Customer(String fname, String lname, String num, Address ad, char gender) {
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
	public void setCreditCard( CreditCard cc) {
		this.cc=cc;
	}

	public double getTotalSpent() {  //for analytics
		double total=0;
		for(Payment p: payments) {
			total+=p.getTotalPaid();
		}
		total+=balance;
		return total;
	}
	
	public void setEBTCard(Ebt ebt) { //can be used to update cards as well like if customer gets a new card
		this.ebtCard=ebt;
	}

	public void setDebitCard(debitCard d) {
		this.dc=d;
	}
	
	protected CreditCard getCreditCard() {
		return cc;
	}
	
	protected debitCard getDebitCard() {
		return dc;
	}
	
	protected Ebt getEbtCard() {
		return ebtCard;
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
	
	public void creditBalance(double num) {
		balance+=num;
	}

	public Long getID() {
		return super.getID();
	}

	public boolean equals(Person p) {
		return super.equals(p);
	}

	public int compareTo(Customer c) {
		return super.compareTo(c);
	}

	public String toString() {
		return super.toString();
	}
	
	public void addSale(SalesOrder theSale) {
		if(allPurchases.contains(theSale)) {
			throw new DuplicateSaleException();
		}
		lastPurchases.push(theSale);
		allPurchases.add(theSale);
		
	}
	

	public void completeSale(SalesOrder theSale) {
		if(allPurchases.contains(theSale)) {
			throw new DuplicateSaleException();
		}
		if(!theSale.isCompleted()) {
			throw new IncompletedSaleException();
		}
		this.addSale(theSale);
		this.balance+=theSale.getTotalSale();
	}





	public void addPayment(Payment thePayment) {
		if(thePayment.getTotalPaid()==0) {
			throw new InvalidNumberException();
		}
		payments.add(thePayment);
		balance-=thePayment.getTotalPaid();
	}
			
	protected SalesOrder getLatestSalesOrder() {
		return lastPurchases.peek();
	}

	public Double processReturn(CustomerReturn custReturn) {
		Double toReturn=0.0;
		SalesOrderDetail detail=findCorrespondingSalesOrderDetail(custReturn);
		SalesOrder sale=null;
		for(SalesOrder s:allPurchases) {
			if(s.getSalesOrderID()==(custReturn.getSalesOrderID())) {
				sale=s;
			}
		}
		if(detail.getQty()<custReturn.getQtyReturned()) {
			throw new InvalidReturnAmtException("items returned cannot exceed the amount of items purchased");
		}
		if(detail.isOnSale() && detail.getDiscountPrice()!=null && sale.isDiscountApplied()) {
			toReturn=detail.getDiscountPrice()*custReturn.getQtyReturned();
		}
		else {
			toReturn=detail.getCurrPrice()*custReturn.getQtyReturned();
		}
		return toReturn;
	}

	private SalesOrderDetail findCorrespondingSalesOrderDetail(CustomerReturn custReturn) {
		if(allPurchases.isEmpty()) {
			throw new NoPurchaseFoundException();
		}
		SalesOrder sale=null;
		for(SalesOrder s:allPurchases) {
			if(s.getSalesOrderID()==(custReturn.getSalesOrderID())) {
				sale=s;
			}
		}
		if(sale==null) {
			throw new NoPurchaseFoundException();
		}
		if(sale.getCustomerID()!=this.getID()) {
			throw new WrongCustomerException();
		}
		SalesOrderDetail toReturn=null;
		ArrayList<SalesOrderDetail>list=sale.getSalesOrderDetails();
		for(SalesOrderDetail d:list) {
			if(d.getUpc()==custReturn.getItemID()) {
				toReturn=d;
			}
		}
		if(toReturn==null) {
			throw new ItemNotFoundException("This item was not part of the order");
		}
		return toReturn;
		
	}
	
}






















