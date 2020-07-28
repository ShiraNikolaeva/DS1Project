package dsFinalProjectMyCopy;
import java.io.Serializable;

public class WrongCustomerException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongCustomerException() {
		super("Wrong Customer!");
	}
}
