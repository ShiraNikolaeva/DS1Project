package dsFinalProjectMyCopy;
import java.io.Serializable;

public class ItemOrder implements Serializable{
	private String UPC;
	private int qtyOrdered;
	private int vendorID;
	
	public ItemOrder(String UPC, int qtyOrdered, int vendorID) {
		this.UPC = UPC;
		this.qtyOrdered = qtyOrdered;
		this.vendorID = vendorID;
		
	}

	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	public int getQtyOrdered() {
		return qtyOrdered;
	}

	public void setQtyOrdered(int qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public int getVendorID() {
		return vendorID;
	}

	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Item Order:\nUPC: " + UPC + "\nQuantity Ordered: " + qtyOrdered + "\nVendor ID: " + vendorID);
		return s.toString();
	}
}
