package dsFinalProjectMyCopy;

public class InvalidCodeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidCodeException() {
		super("security code must be exactly 3 or 4 digits");
	}

}
