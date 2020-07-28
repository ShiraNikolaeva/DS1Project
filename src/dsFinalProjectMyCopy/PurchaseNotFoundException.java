package dsFinalProjectMyCopy;

public class PurchaseNotFoundException extends RuntimeException {

	public PurchaseNotFoundException() {
		super("This purchase order was not found. ");
	}
}
