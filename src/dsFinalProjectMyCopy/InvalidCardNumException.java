package dsFinalProjectMyCopy;

public class InvalidCardNumException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidCardNumException() {
		super("card number must be exactly 16 digits");
	}
}
