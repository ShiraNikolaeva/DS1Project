package dsFinalProjectMyCopy;

public class InvalidPinException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPinException() {
		super("pin must be exactly 4 digits");
	}

}
