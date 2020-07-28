package dsFinalProjectMyCopy;
import java.io.Serializable;

public class ScannedItem implements Serializable{
	private String upc;
	private int qty;
	
	public ScannedItem(String upc, int qty) {
		this.upc = upc;
		this.qty = qty;
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
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Scanned Item:\nUPC: " + upc + "\nQuantity: " + qty);
		return s.toString();
	}
}
