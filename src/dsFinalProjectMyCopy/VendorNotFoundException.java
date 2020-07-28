package dsFinalProjectMyCopy;

public class VendorNotFoundException extends RuntimeException {
	public VendorNotFoundException() {
		super("The vendor does not exist!");
	}
}
