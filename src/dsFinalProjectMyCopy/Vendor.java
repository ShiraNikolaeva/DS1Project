package dsFinalProjectMyCopy;
/*
 * Made by Shira Nikolaeva
 * Date: 12/17/2019
*/
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class Vendor implements Serializable {
	
	private int vendorId;
	private String vendorName;
	private String phoneNumber;
	private Address address;
	private Stack<PurchaseOrder> lastOrders=new Stack<>();
	private ArrayList <PurchaseOrder>orders=new ArrayList<>();
	private static long lastVendorNO=0;
	private int itemCount; //amount of items the vendor has
	
	
	
	
	public Vendor(String vendorName, Address address,String phoneNumber) {
		this.vendorName=vendorName;
		setAddress(address);
		setPhoneNumber(phoneNumber);
		this.vendorId=(int) lastVendorNO;
		lastVendorNO++;
		itemCount =0;
	}
	public int getVendorID() {
		return this.vendorId;
	}
	public int getItemCount() {
		return itemCount;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void incrementItemCount() {
		itemCount++;
	}
	//sn making setters because a company can move and it can have several representatives that can be rotated over time(assume they have different phone numbers)
	//sn not allowing to change name. if a vendor is changing the name consider it a new vendor
	public void setPhoneNumber(String num) {
		if(num==null) {
			throw new NullPointerException();
		}
		if(!validatePhoneNum(num)) {
			throw new InvalidNumberException();
		}
		this.phoneNumber=num;
	}
	
	private boolean validatePhoneNum(String num) {
		//sn validate that its 10 digits
		if (num.matches("[0-9]+") && num.length() == 10) {
			return true;
		}
		return false;
	}

	public void setAddress(Address address) {
		this.address=address;		
	}
	
	public void addPurchaseOrder(PurchaseOrder order) {
		lastOrders.push(order);
		orders.add(order);
	}
	protected PurchaseOrder getPurchaseOrder(Long ID) {
		PurchaseOrder toReturn=null;
		for(PurchaseOrder po:orders) {
			if(po.getPurchaseOrderID()==ID) {
				toReturn=po;
			}
		}
		return toReturn;
	}
	public String toString() {
		return "ID: "+vendorId+" Name: "+vendorName+" Address: "+address.toString()+" Phone Number: "+phoneNumber;
	}

}





















