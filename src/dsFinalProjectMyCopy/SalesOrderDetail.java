package dsFinalProjectMyCopy;

import java.io.Serializable;

public class SalesOrderDetail implements Serializable, Comparable<SalesOrderDetail>{

	private long salesOrderID;
	private String upc;
	private String itemDescrip;
	private int qty;
	private double currentPrice;
	private Double discountPrice;
	private boolean onSale;
	private boolean isEBT; //is this item covered by ebt
	private ITEMTYPE type;
	
	public SalesOrderDetail(Long orderID,String upc, String descrip, int qty, double price, double discountPrice, boolean onSale, boolean isEBT, ITEMTYPE type) {	
		
		this.salesOrderID=orderID;
		this.upc=upc;
		this.itemDescrip=descrip;
		this.qty=qty;
		this.currentPrice=price;
		this.discountPrice=discountPrice;
		this.onSale=onSale;
		this.isEBT=isEBT;
		this.type=type;		
	}
	
	@Override
	public String toString() {
		StringBuilder str=new StringBuilder();
		str.append(" order ID: "+salesOrderID);
		str.append(", upc: "+upc);
		str.append(", description: "+itemDescrip);
		str.append(", quantity: "+qty);
		str.append(", current price: "+currentPrice);
		str.append(", discount price: "+discountPrice);
		str.append(", This item is" +(onSale?" ": " not ")+ "on sale");
		str.append(", Paid with EBT: "+(isEBT? "yes":"no"));
		str.append(", Item type: "+type.toString().toLowerCase());
		str.append("\n");	
		return str.toString();		
	}
	
	@Override
	public boolean equals(Object o) {
		if (this==o) {
			return true;
		}
		if(o==null) {
			return false;
		}
		if(!this.getClass().equals(o.getClass())) {
			return false;
		}
		SalesOrderDetail other=(SalesOrderDetail) o;
		if(!other.getUpc().equals(this.getUpc())) {
			return false;
		}
		if(this.getSalesID()!=other.getSalesID()) {
			return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(SalesOrderDetail s) { //what should we compare on?
		return this.upc.compareTo(s.getUpc());
	}
	
	
	//getters
	public long getSalesID() {
		return this.salesOrderID;
	}
	
	public String getUpc() {
		return upc;
	}
	
	public boolean isTaxable() {
		if (type.getSymbol().equals(TAXABLE.TAXED)){
			return true;
		}
		return false;
	}
	
	public String getDescrip() {
		return itemDescrip;
	}
	public int getQty() {
		return qty;
	}
	public double getCurrPrice() {
		return currentPrice;
	}
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public boolean isOnSale() {
		return onSale;
	}
	public boolean isEBT() {
		return isEBT;
	}
	public String getItemType() {
		return type.toString();
	}
}
