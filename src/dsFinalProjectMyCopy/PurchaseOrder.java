package dsFinalProjectMyCopy;
/*
 * Made by Shira Nikolaeva
 * Date: 12/17/2019
*/
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PurchaseOrder implements Serializable, Comparable<PurchaseOrder> {

	private Long purchaseOrderID;
	private LocalDate purchaseDate;
	private int vendorID;
	private double totalAmount;
	private ArrayList<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();
	private ArrayList<Payment> payments = new ArrayList<>();
	private static long lastOrderID = 0;

	public PurchaseOrder(int vendorID) {
		this.vendorID = vendorID;
		this.purchaseDate = LocalDate.now();
		this.purchaseOrderID = lastOrderID;
		lastOrderID++;
	}

	public Long getPurchaseOrderID() {
		return this.purchaseOrderID;
	}

	public LocalDate getPurchaseDate() {
		return this.purchaseDate;
	}

	public int getVendorID() {
		return this.vendorID;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void addOrderDetails(PurchaseOrderDetail details) {
		purchaseOrderDetails.add(details);
	}

	public void addOrderDetails(String upc, int qtyOrdered) {
		addOrderDetails(new PurchaseOrderDetail(upc, qtyOrdered));
	}

	public void receiveItem(String upc, int qty, double unitCost) {
		if (purchaseOrderDetails.isEmpty()) {
			throw new NoDetailsFoundException();
		}
		PurchaseOrderDetail detail = null;
		for (PurchaseOrderDetail pod : purchaseOrderDetails) {
			if (pod.getUPC().equals(upc)) {
				detail = pod;
			}
		}
		if (detail == null) {
			throw new NoDetailsFoundException();
		}
		detail.setDetails(qty, unitCost);
		totalAmount += qty * unitCost;
	}
	public void receiveItem(ReceivedItem item) {
		receiveItem(item.getUpc(),item.getQty(),item.getUnitCost());
	}

	public void makePayment(Payment thePayment) {
		if (thePayment.getOrderID() != this.purchaseOrderID) {
			throw new PurchaseORderIDMismatchException();
		}
		payments.add(thePayment);
		totalAmount -= thePayment.getTotalPaid();
	}

	// sn comparison only on id because it's unique
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()
				|| this.purchaseOrderID != ((PurchaseOrder) o).getPurchaseOrderID()) {
			return false;
		}
		return true;
	}

	public String toString() {
		return "OrderID: " + purchaseOrderID + " Date: " + purchaseDate + " VendorID: " + vendorID + " Total: "
				+ totalAmount;
	}

	@Override
	// sn compares the ids
	public int compareTo(PurchaseOrder po) {
		if (po == null) {
			throw new NullPointerException();
		}
		return Long.compare(this.purchaseOrderID, po.getPurchaseOrderID());
	}

}
