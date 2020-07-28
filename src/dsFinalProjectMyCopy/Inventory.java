package dsFinalProjectMyCopy;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
public class Inventory implements Serializable{
	ArrayList<Item> items;
	
	public Inventory () {
		items = new ArrayList<Item>();
	}

	public ArrayList<Item> getItems() {
		ArrayList<Item> copy = new ArrayList<Item>();
		for (int i = 0;i <items.size();i++) {
			copy.add(items.get(i));
		
		}
		return copy;
	}
	
	public void restockItem(String upc, int qtyReceived) {
		Item i =getItem(upc);
		int qtyInventory = i.getQtyInInventory();
		int newQty = qtyInventory + qtyReceived;
		i.setQtyInInventory(newQty);
	}
	
	public void sellItem(String upc, int qty) {
		Item i =getItem(upc);
		int qtyInventory = i.getQtyInInventory();
		int newQty = qtyInventory-qty;
		i.setQtyInInventory(newQty);
		i.updateTimesSold();
	}
	
	public void addNewItem(Item i) {
		items.add(i);	
	}
	
	public double getCurrentPrice (String upc) {
		Item i =getItem(upc);
		return i.getSalePricePerUnit();
		
	}
	public double getDiscountedPrice(String upc) {
		Item i =getItem(upc);
		LocalDate currentDate = LocalDate.now();
		return i.getDiscountPrice(currentDate);
	}
	
	public ArrayList<LowItem> getBelowRestockLevel(){
		ArrayList<LowItem> low = new ArrayList<LowItem>();
		for (int i = 0;i <items.size();i++) {
			int currentQty = items.get(i).getQtyInInventory();
			int restockLvl = items.get(i).getRestockQtyLevel();
			if (currentQty<restockLvl) {
				LowItem l = new LowItem(items.get(i).getUniversalProductCode(), currentQty, restockLvl);
				low.add(l);
			}
		}
		return low;
	}
	
	public boolean hasItem(String upc) {
		for (int i = 0; i <items.size();i++) {
			if (upc.equals(items.get(i).getUniversalProductCode())){
				return true;
			}
		}
		return false;		
	}
	
	public Item getItem(String upc) throws ItemNotFoundException{
		if (hasItem(upc)) {
			for(Item i: items) {
				if(i.getUniversalProductCode().equals(upc)) {
					return i;
				}
			}
		}
		throw new ItemNotFoundException("There is no item with the upc " + upc + " in the inventory.");
	}
	
	public void setRestockLevel (String upc, int qty) {
		Item i=getItem(upc);
		i.setRestockQtyLevel(qty);
	}
	
	public Item getItemWithMostSales() {
		Item max = items.get(0);
		for (int i = 1;i <items.size();i++) {
			if (items.get(i).getTimesSold()> max.getTimesSold()) {
				max = items.get(i);
			}
		}
		return max;
	}
	
	
}
