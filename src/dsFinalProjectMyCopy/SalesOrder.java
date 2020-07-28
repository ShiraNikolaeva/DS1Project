package dsFinalProjectMyCopy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.PriorityQueue; //shud we use javas or make my own??

public class SalesOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static long lastSalesID = 0;
	private Long salesOrderID;
	private LocalDate salesDate;
	private Customer customer;
	private Long cashierID;
	private int registerNum;
	private double EBTItemsTotal;
	private double totalSale;
	private double totalNonDiscount;
	private Payment payment;
	private boolean isCompleted;
	private boolean discountApplied;
	private ArrayList<SalesOrderDetail> salesOrderDetails;
	
	
	public SalesOrder(Customer customer,Long cashierID, ArrayList<SalesOrderDetail> details) {  
		lastSalesID++;
		this.salesOrderID=lastSalesID;
		salesDate=LocalDate.now();
		totalSale=0;
		totalNonDiscount=0;
		EBTItemsTotal=0;
		isCompleted=false;	
		discountApplied=false;
		payment=null;
		this.customer=customer; 
		this.cashierID=cashierID; //check that this is valid but cant check here cuz idk the valid cashier id's
		this.salesOrderDetails=details;		
	}
	
	public SalesOrder (Customer customer, Long cashierID) {	
		lastSalesID++;
		this.salesOrderID=lastSalesID;
		salesDate=LocalDate.now();
		totalSale=0;
		totalNonDiscount=0;
		EBTItemsTotal=0;
		isCompleted=false;
		discountApplied=false;
		payment=null;
		this.customer=customer; 
		this.cashierID=cashierID;
	}
	
	public SalesOrder (Long cashierID) {	
		lastSalesID++;
		this.salesOrderID=lastSalesID;
		salesDate=LocalDate.now();
		this.customer=null;
		payment=null;
		totalSale=0;
		totalNonDiscount=0;
		EBTItemsTotal=0;
		isCompleted=false;	
		discountApplied=false;
		this.cashierID=cashierID;
	}
	
	public void makePayment(Payment p) { 
		if(!isCompleted) {
			throw new OrderCompleteException("Cannot make payment until order is complete");
		}
		if(payment==null) {
			ArrayList<PaymentDetail> details =p.getPaymentDetails();
			for (PaymentDetail pd: details) {
				if(pd.getPayMethod().equals("EBTCARD")) {
					//validate that dollar amout is <= to the limit 
					if(customer!=null && customer.getEbtCard().getCurrentBalance()<pd.getAmount()) {
						throw new InsufficientFundsException();
					}
					if(customer!=null) {
						customer.getEbtCard().decrementCurrBalance(pd.getAmount());
					}
					//if customer is null, we just dont deal with making sure there is enough balance remaining on the ebt card, this is too hard ot simulate sorry
				}
			} 
			this.payment=p;
			if(customer!=null) {
				customer.addPayment(p);
			}
		}
		else {
			throw new PaymentMadeAlreadyException();
		}
	}
	
	//ASSUMPTION: if they make a payment with an EBT and their NOT a customer, we cannot deal with the ebt info/validating that theirs enough funds, 
	//the credit card processing technology is responsible for that anyhow, NOT the POS system
	
	public void applyDiscount(double minimumPurchase) { //RP-make sure this is called BEFORE payment is made! otherwise will need to refund and stuff-COMPLICATED. 
		if(totalNonDiscount>=minimumPurchase) {
			discountApplied=true;
			for(SalesOrderDetail d: salesOrderDetails) {
				double nowSpent, beforeSpent;
				if(d.isOnSale()) {
					beforeSpent=(d.getCurrPrice()*d.getQty());
					nowSpent=(d.getDiscountPrice()*d.getQty());
					totalSale-=beforeSpent;
					totalSale+=nowSpent;
					if(d.isEBT()) {
						EBTItemsTotal-=beforeSpent;
						EBTItemsTotal+=nowSpent;
					}
				}				
			}
			return;
		}
		throw new CannotApplyDiscountException("minimum purchase has not been met"); //RP-make sure in store/main to check if totalNonDiscount>=minimumPurchase before doing apply discount
	}
	
	
	public void addDetail (SalesOrderDetail detail ) {
		if(!isCompleted) {
			salesOrderDetails.add(detail);
			double spent=(detail.getCurrPrice()*detail.getQty());
			totalSale+=spent;
			if (!detail.isOnSale()) {
				totalNonDiscount+=spent;
			}
			if(detail.isEBT()) { //shudnt i also be checking if this customer is even paying with an ebt??? but how shud i know.....					
				EBTItemsTotal+=spent; 
			}
		}
		else {
			throw new OrderCompleteException();
		}
	}
	
	public void addDetails(ArrayList<SalesOrderDetail> details) {
		if(!isCompleted) {
			for(SalesOrderDetail detail: details) {
				salesOrderDetails.add(detail);
				double spent=(detail.getCurrPrice()*detail.getQty());
				totalSale+=spent;
				if (!detail.isOnSale()) {
					totalNonDiscount+=spent;
				}
				if(detail.isEBT()) { //shudnt i also be checking if this customer is even paying with an ebt??? but how shud i know.....					
					EBTItemsTotal+=spent;
				}
			}
		}
		else {
			throw new OrderCompleteException();
		}
	}
	
	public void removeDetail(SalesOrderDetail detail) {
		if(!isCompleted) {
			for(SalesOrderDetail d: salesOrderDetails) {
				if(d.equals(detail)) {					
					double spent;
					if (discountApplied && detail.isOnSale()) {
						spent= (detail.getDiscountPrice()*detail.getQty());
					}
					else {
						spent=(detail.getCurrPrice()*detail.getQty());
						if(!detail.isOnSale()) {
							totalNonDiscount-=spent;
						}
					}
					totalSale-=spent;
					if(detail.isEBT()) { //shudnt i also be checking if this customer is even paying with an ebt??? but how shud i know.....					
						EBTItemsTotal-=spent;
					}				
					salesOrderDetails.remove(d);
					return;
				}
			}
			throw new DetailNotFoundException();
		}
		else {
			throw new OrderCompleteException();
		}
	}
	
	public double getTotalTaxable() {
		double totalToTax=0.0;
		for(SalesOrderDetail d: salesOrderDetails) {
			if(d.isTaxable()) {
				if(discountApplied && d.isOnSale()) {
					totalToTax+=d.getDiscountPrice();
				}
				else {
					totalToTax+=d.getCurrPrice();
				}
			}
		}
		return totalToTax;
	}
	
	public Long getSalesOrderID() {
		return salesOrderID;
	}

	public LocalDate getSalesDate() {
		return LocalDate.of(salesDate.getYear(), salesDate.getMonth(), salesDate.getDayOfMonth());
	}
	
	public Long getCustomerID() {
		if(customer == null) {
			return null;
		}
		return customer.getID();
	}

	public Long getCashierID() {
		return cashierID;
	}

	public int getRegisterNum() {
		return registerNum;
	}

	public void setRegisterNum(int registerNum) {
		this.registerNum = registerNum;
	}

	public double getTotalEBTitems() {
		return EBTItemsTotal;
	}

	public double getTotalSale() {
		return totalSale;
	}

	public double getTotalNonDiscount() {
		return totalNonDiscount;
	}

	protected Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public boolean isDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(boolean discountApplied) {
		this.discountApplied = discountApplied;
	}

	public ArrayList<SalesOrderDetail> getSalesOrderDetails() {
		return salesOrderDetails;
	}

	public void setSalesOrderDetails(ArrayList<SalesOrderDetail> salesOrderDetails) {
		this.salesOrderDetails = salesOrderDetails;
	}

	public String printReceipt() {
		StringBuilder str=new StringBuilder("Sales Order "+salesOrderID+": \n");
		str.append("Date: "+salesDate+"\n");
		str.append("Cashier: "+cashierID+"     Register #: "+registerNum+"\n");
		if(customer!=null) {
			str.append(customer.getLastName()+", "+customer.getFirstName()+"\n");
			if(customer.getAdress()!=null) {
				str.append(customer.getAdress()+"\n");
			}
			str.append(customer.getPhoneNumber()+"\n");
		}
		ArrayList<SalesOrderDetail> aluminum=new ArrayList<>();
		ArrayList<SalesOrderDetail> bakery=new ArrayList<>();		
		ArrayList<SalesOrderDetail> chicken=new ArrayList<>();
		ArrayList<SalesOrderDetail> fish=new ArrayList<>();
		ArrayList<SalesOrderDetail> fruit=new ArrayList<>();
		ArrayList<SalesOrderDetail> grocery=new ArrayList<>();
		ArrayList<SalesOrderDetail> magazines=new ArrayList<>();
		ArrayList<SalesOrderDetail> meat=new ArrayList<>();
		ArrayList<SalesOrderDetail> papergoods=new ArrayList<>();
		
		for(SalesOrderDetail d: salesOrderDetails) {					
			switch(d.getItemType()) {
				case "ALUMINUM": aluminum.add(d);
								 break;
				case "BAKERY": bakery.add(d);
							   break;
				case "CHICKEN": chicken.add(d);
				   				break;
				case "FISH": fish.add(d);
   							 break; 	
				case "FRUIT": fruit.add(d);
							  break;
				case "GROCERY": grocery.add(d);
								break;
				case "MAGAZINES": magazines.add(d);
								  break;
				case "MEAT": meat.add(d);
							 break;
				case "PAPERGOODS": papergoods.add(d);				
			}
		}
		if(!aluminum.isEmpty()) {
			str.append("ALUMINUM \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: aluminum) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!bakery.isEmpty()) {
			str.append("BAKERY \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: bakery) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!chicken.isEmpty()) {
			str.append("CHICKEN \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: chicken) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!fish.isEmpty()) {
			str.append("FISH \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: fish) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!fruit.isEmpty()) {
			str.append("FRUIT \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: fruit) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!grocery.isEmpty()) {
			str.append("GROCERY \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: grocery) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!magazines.isEmpty()) {
			str.append("MAGAZINES \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: magazines) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!meat.isEmpty()) {
			str.append("MEAT \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: meat) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		if(!papergoods.isEmpty()) {
			str.append("PAPER GOODS \n");
			str.append("--------- \n");
			for(SalesOrderDetail sd: papergoods) {
				str.append(sd.getQty()+"  "+sd.getDescrip()+"  @ 1/"+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())+""
						+ "   "+ (discountApplied && sd.isOnSale()? sd.getDiscountPrice():sd.getCurrPrice())*sd.getQty() +"\n");
			}
		}
		str.append("============================\n");
		str.append("Tax On: $"+getTotalTaxable()+"       $"+ (getTotalTaxable()*8.75) +" \n"); //FORMATTTTTTT 2 PLACES
		str.append("============\n");
		str.append("Total: $"+ (totalSale+(getTotalTaxable()*8.75)) +"\n\n");
		for(PaymentDetail pd: payment.getPaymentDetails()) {
			str.append(pd.getPayMethod().toLowerCase()+" tend:   "+pd.getAmount()+"\n");
			if(pd.getCardID()!=null) {
				str.append(pd.getCardID()+"\n");
			}
		}
		return str.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("ID: "+salesOrderID+", ");
		sb.append("sale date: "+salesDate+", ");
		sb.append("customer ID: "+customer.getID()+", ");
		sb.append("cashier ID: "+cashierID+", ");
		sb.append("register num: "+registerNum+", ");
		sb.append("EBT max total: "+EBTItemsTotal+", ");
		sb.append("total sale: "+totalSale+", ");
		sb.append("total nondiscount: "+totalNonDiscount+", ");
		sb.append("payment info: \n");
		for(PaymentDetail p: payment.getPaymentDetails()) {
			sb.append(p.toString()+"\n");
		}
		sb.append("completed: "+isCompleted+", ");
		sb.append("discount applied: "+discountApplied+", ");
		sb.append("sales order details: ");
		for(SalesOrderDetail d: salesOrderDetails) {
			sb.append(d.toString()+"\n");
		}
		return sb.toString();
	}
	
	
	
	
	
}
