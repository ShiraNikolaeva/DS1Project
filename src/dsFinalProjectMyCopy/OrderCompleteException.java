package dsFinalProjectMyCopy;

public class OrderCompleteException extends RuntimeException {

	public OrderCompleteException() {
		super("order is already complete");
	}
	
	public OrderCompleteException(String message) {
		super(message);
	}
}
