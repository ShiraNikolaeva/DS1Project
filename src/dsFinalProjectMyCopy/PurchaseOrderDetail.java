package dsFinalProjectMyCopy;
/*
 * Made by Shira Nikolaeva
 * Date: 12/17/2019
*/
import java.io.Serializable;
import java.time.LocalDate;

public class PurchaseOrderDetail implements Serializable {
	
	private static long lastID=0;
	private long purchaseOrderID;
	private String upc;
	private int qtyOrdered;
	private int qtyReceived;
	private double unitCost;
	private double lineTotal;
	private LocalDate dateReceived;

	public PurchaseOrderDetail( String upc, int qtyOrdered) {
		if (qtyOrdered <= 0 || unitCost <= 0.0) {
			throw new InvalidNumberException();
		}
		this.purchaseOrderID=lastID;
		lastID++;
		this.upc = upc;
		this.qtyOrdered = qtyOrdered;
	}
	public PurchaseOrderDetail(ItemOrder io) {
		new PurchaseOrderDetail(io.getUPC(),io.getQtyOrdered());
	}
	public String getUPC() {
		return this.upc;
	}

	public void receivePurchaseOrderDetail(int qtyReceived, double unitCost) {
		setDetails(qtyReceived,unitCost);
		this.dateReceived = LocalDate.now();
	}

	public void receivePurchaseOrderDetail(int rtyReceived, double unitCost, LocalDate date) {
		setDetails(qtyReceived,unitCost);
		this.dateReceived=date;
	}

	public void setDetails(int qtyReceived, double unitCost) {
		// sn allow 0 to address a situation when ordered a discontinued item
		if (qtyReceived < 0) {
			throw new InvalidNumberException();
		}
		this.qtyReceived = qtyReceived;
		this.unitCost = unitCost;
		this.lineTotal=qtyReceived*unitCost;
	}
}
