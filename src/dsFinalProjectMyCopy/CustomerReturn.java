package dsFinalProjectMyCopy;
/*
 * Made by Shira Nikolaeva 1/5/2020
 * */

import java.io.Serializable;

public class CustomerReturn implements Comparable<CustomerReturn>, Serializable {

	private long salesOrderID;
	private String itemID;
	private int qtyReturned;
	private String reason;

	public CustomerReturn(long saleID, String upc, int qty, String reason) {
		this.salesOrderID = saleID;
		this.itemID = upc;
		this.qtyReturned = qty;
		this.reason = reason;
	}

	public long getSalesOrderID() {
		return this.salesOrderID;
	}

	public String getItemID() {
		return this.itemID;
	}

	public int getQtyReturned() {
		return this.qtyReturned;
	}

	public String getReason() {
		return this.reason;
	}

	public boolean equals(CustomerReturn cr) {
		return (this.salesOrderID == (cr.salesOrderID) && this.itemID.equals(cr.itemID));
	}

	public String toString() {
		return "Sales Order ID: " + salesOrderID + " UPC: " + itemID + " QTY Returned: " + qtyReturned + " Reason "
				+ reason;
	}

	@Override
	public int compareTo(CustomerReturn cr) {
		return this.compareTo(cr);
	}

}
