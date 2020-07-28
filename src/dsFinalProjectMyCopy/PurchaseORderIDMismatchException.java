package dsFinalProjectMyCopy;
import java.io.Serializable;

public class PurchaseORderIDMismatchException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PurchaseORderIDMismatchException() {
		super("Purchase Order ID does not exist!");
	}

}
