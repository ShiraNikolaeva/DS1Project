package dsFinalProjectMyCopy;
import java.io.Serializable;

public class LowItem implements Serializable{
	private String upc;
	private int currentInvQty;
	private int restockLevel;
	
	public LowItem(String upc, int currentInvQty, int restockLevel) {
		this.upc= upc;
		this.currentInvQty = currentInvQty;
		this.restockLevel = restockLevel;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public int getCurrentInvQty() {
		return currentInvQty;
	}

	public void setCurrentInvQty(int currentInvQty) {
		this.currentInvQty = currentInvQty;
	}

	public int getRestockLevel() {
		return restockLevel;
	}

	public void setRestockLevel(int restockLevel) {
		this.restockLevel = restockLevel;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("LowItem:\nUPC: " + upc + "\nCurrent Inventory Quantity: " + currentInvQty
				+"\nRestock Level: "+ restockLevel);
		return s.toString();
	}
}
