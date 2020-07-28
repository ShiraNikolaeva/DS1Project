package dsFinalProjectMyCopy;

public class InvalidPurchaseOrderException extends RuntimeException {
	public InvalidPurchaseOrderException() {
		super("The Purchase Order with that ID does not exist");
	}
}
