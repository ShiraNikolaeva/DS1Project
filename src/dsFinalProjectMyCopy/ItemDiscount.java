package dsFinalProjectMyCopy;
import java.time.LocalDate;
import java.io.Serializable;

public class ItemDiscount implements Serializable {
	private String UPC;
	private LocalDate startDate;
	private LocalDate endDate;
	private int qtyLimit;
	private double discountPrice;

	public ItemDiscount(String UPC, LocalDate startDate, LocalDate endDate, int qtyLimit, double discountPrice) {
		this.UPC = UPC;
		this.startDate = startDate;
		this.endDate = endDate;
		this.qtyLimit = qtyLimit;
		this.discountPrice = discountPrice;
	
	}

	public String getUPC() {
		return UPC;
	}

	public void setUPC(String uPC) {
		UPC = uPC;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getQtyLimit() {
		return qtyLimit;
	}

	public void setQtyLimit(int qtyLimit) {
		this.qtyLimit = qtyLimit;
	}

	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("ITEM DISCOUNT: \nUPC: " + UPC + "\nStart Date: " + startDate + "\nEnd Date: " + endDate + "\nQuantity Limit: " + qtyLimit
				+ "\nDiscount Price: " + discountPrice+"\n");
	
		return s.toString();
	}
}