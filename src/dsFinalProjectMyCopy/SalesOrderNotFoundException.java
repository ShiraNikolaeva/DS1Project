package dsFinalProjectMyCopy;

public class SalesOrderNotFoundException extends RuntimeException {
	public SalesOrderNotFoundException() {
		super("The Sales Order is not found!");

	}
}
