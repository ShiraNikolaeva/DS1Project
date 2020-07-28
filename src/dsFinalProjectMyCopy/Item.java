package dsFinalProjectMyCopy;
import java.time.LocalDate;
import java.util.Stack;
import java.io.Serializable;


public class Item implements Comparable<Item>, Serializable{
	private String universalProductCode;
	private String description;
	private double salePricePerUnit;
	private int qtyInInventory;
	private UNITMEASURE unitMeasure;
	private int restockQtyLevel;
	private ITEMTYPE itemType;
	private boolean isEBT;
	private int vendorID;
	private int timesSold;
	private Stack<ItemDiscount> discounts;
	
	public Item(String upc, String desc, Double price, String type, String unitMeasure, boolean ebt, int vendorid) throws InvalidUPCException{	
		if (verifyUPC(upc)) {
			
			universalProductCode = upc;
		}
		else {
			throw new InvalidUPCException();
		}
		description = desc;
		isEBT=ebt;
		salePricePerUnit = price;
		itemType = ITEMTYPE.valueOf(type);
		this.unitMeasure = UNITMEASURE.valueOf(unitMeasure);
		vendorID = vendorid; 
		timesSold = 0;
		discounts = new Stack<ItemDiscount>();
	}
	public Item (String upc, String desc, Double price, ITEMTYPE type, UNITMEASURE unitMeasure, int vendorid) throws InvalidUPCException{
		if (verifyUPC(upc)) {
			
			universalProductCode = upc;
		}
		else {
			throw new InvalidUPCException();
		}
		description = desc;
		salePricePerUnit = price;
		itemType = type;
		this.unitMeasure = unitMeasure;
		vendorID = vendorid; 
		discounts = new Stack<ItemDiscount>();
	}
	
	public void addDiscount (LocalDate start, LocalDate end, int qtyLimit, Double discountPrice) {
		ItemDiscount i = new ItemDiscount(universalProductCode,start,end,qtyLimit,discountPrice);
		addToDiscounts(i);
		
	}
	
	public void addDiscount(ItemDiscount theDiscount) {	
		addToDiscounts(theDiscount);
	}
	
	
	private void addToDiscounts(ItemDiscount i) throws InvalidDiscountException{
		if (discounts.isEmpty()) {
			discounts.push(i);
			
		}
		else {
			Stack<ItemDiscount> temp = new Stack<ItemDiscount>();
			int startMonth = i.getStartDate().getMonthValue();
			int endMonth = i.getEndDate().getMonthValue();
			int startYear = i.getStartDate().getYear();
			int endYear = i.getEndDate().getYear();
			ItemDiscount disc = discounts.pop();
			temp.push(disc);
			boolean firstTime = true;
			//If year of latest discount does not match up then automatically add this discount
			if(disc.getStartDate().getYear()!=startYear &&
					disc.getStartDate().getYear()!= endYear
					&&disc.getEndDate().getYear()!= startYear
					&& disc.getEndDate().getYear()!= endYear) {
				
			}
			else {
				
				while ((discounts.empty() == false  || firstTime == true)&& disc.getStartDate().getYear()==startYear &&
						disc.getStartDate().getYear()== endYear
						&&disc.getEndDate().getYear()== startYear
						&& disc.getEndDate().getYear()== endYear){
					firstTime = false;
					
					
						//if same upc and month is the same then throw exception
					 if (disc.getUPC().equals(i.getUPC())){
						if (disc.getStartDate().getMonthValue()==startMonth ||
								disc.getStartDate().getMonthValue() ==endMonth ||
								disc.getEndDate().getMonthValue() ==startMonth ||
								disc.getEndDate().getMonthValue()== endMonth) {
							
								
								throw new InvalidDiscountException();
							
						}
					 }
					
				}
				 
				
			}	
			while (temp.empty()==false) {
				discounts.push(temp.pop());
			}
			discounts.push(i);
			}

	}
	//if there is no discount, return null
	public Double getDiscountPrice(LocalDate currentDate) {
		Double price = -1.5;
		Stack<ItemDiscount> temp = new Stack<ItemDiscount>();
		while(discounts.empty()== false) {
			ItemDiscount disc = discounts.pop();
			temp.push(disc);
			LocalDate startDate = disc.getStartDate();
			LocalDate endDate = disc.getEndDate();
			if (currentDate.isEqual(startDate)|| currentDate.isEqual(endDate)||
					(currentDate.isAfter(startDate)&&currentDate.isBefore(endDate))) {
					price= disc.getDiscountPrice();
					return price;
			}
		}
		
		while(temp.empty()==false) {
			discounts.push(temp.pop());
		}
//		if (price == -1.5) {
//			throw new NoDiscountFoundException();
//		}
		return null;
		
	}

	public Item clone() {
		return new Item(universalProductCode, description, salePricePerUnit, itemType, unitMeasure, vendorID);
	}

	public String getUniversalProductCode() {
		
		return universalProductCode;
	}

	public  boolean isEBT() {
		return isEBT;
	}


	public int getTimesSold() {
		return timesSold;
	}
	public void updateTimesSold() {
		timesSold++;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getSalePricePerUnit() {
		return salePricePerUnit;
	}


	public void setSalePricePerUnit(double salePricePerUnit) {
		this.salePricePerUnit = salePricePerUnit;
	}


	public int getQtyInInventory() {
		return qtyInInventory;
	}


	public void setQtyInInventory(int qtyInInventory) {
		this.qtyInInventory = qtyInInventory;
	}


	public UNITMEASURE getUnitMeasure() {
		return unitMeasure;
	}


	public void setUnitMeasure(UNITMEASURE unitMeasure) {
		this.unitMeasure = unitMeasure;
	}


	public int getRestockQtyLevel() {
		return restockQtyLevel;
	}


	public void setRestockQtyLevel(int restockQtyLevel) {
		this.restockQtyLevel = restockQtyLevel;
	}


	public ITEMTYPE getItemType() {
		return itemType;
	}


	public void setItemType(ITEMTYPE itemType) {
		this.itemType = itemType;
	}


	public int getVendorID() {
		return vendorID;
	}


	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}


	public Stack<ItemDiscount> getDiscounts() {
		Stack<ItemDiscount> discountCopy = new Stack<ItemDiscount>();
		Stack<ItemDiscount> temp = new Stack<ItemDiscount>();
		while (discounts.empty()==false) {
			temp.push(discounts.pop());
		}
		while(temp.empty()==false) {
			ItemDiscount disc = temp.pop();
			discounts.push(disc);
			discountCopy.push(disc);
		}
		return discountCopy;
	}




	private static boolean verifyUPC(String UPC) {
		
		if (UPC.length()!= 12 && UPC.length()!= 13 && UPC.length()!= 8) {
			return false;
		}
		int checkDigit = Integer.parseInt(UPC.substring(UPC.length()-1));
		UPC = UPC.substring(0,UPC.length()-1);
		boolean odd = true;
		int total = 0;
		for (int  i = 0; i <UPC.length();i++) {
			if (odd == true) {
				total+=  3* Integer.parseInt(UPC.substring(i,i+1));
				odd = false;
			}
			else {
				total += Integer.parseInt(UPC.substring(i,i+1));
				odd = true;
			}
		}
		
		int num = nearestMultipleOfTen(total);
		if (num - total ==checkDigit) {
			return true;
		}
		else {
			return false;
		}
	}


	private static int nearestMultipleOfTen(int n) {
		 int rem;
		   rem=n%10;
		   
		   if(rem>5)
		   {
		     n=n+(10-rem);
		   }
		   else
		   {
		    n=n-rem; 
		   } 
		   return n;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Item other = (Item)obj;
		if  (this.getUniversalProductCode() != other.getUniversalProductCode()) {
			return false;
		}
		
		return true;
	}
	 @Override
	public int compareTo(Item i) {
		return this.getUniversalProductCode().compareTo(i.getUniversalProductCode());
		
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("UPC: " + universalProductCode + "\nDescription: " + description
				+ "\nSale Price Per Unit: " + salePricePerUnit + "\nQuantity in Inventory: " +
				qtyInInventory + "\nUnit Measure: " + unitMeasure + 
				"\nRestock Quantity Level: " + restockQtyLevel + "\nItem Type: "+
				itemType + "\nVendorID: " + vendorID +"\nTimes Sold: "+ timesSold
				);
		return s.toString();
	}
	
}
