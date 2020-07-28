package dsFinalProjectMyCopy;
import java.io.Serializable;
public class ReceivedItem implements Serializable{
	private String upc;
	private int qty;
	private double unitCost;
	
	public ReceivedItem(String upc, int qty, double unitCost) {
		this.upc = upc;
		this.qty = qty;
		this.unitCost = unitCost;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Recieved Item:\nUPC: " + upc + "\nQuantity: " + qty + "\nUnit Cost: "+ unitCost);
		return s.toString();
	}
}
