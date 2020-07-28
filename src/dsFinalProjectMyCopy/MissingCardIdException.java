package dsFinalProjectMyCopy;

public class MissingCardIdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingCardIdException() {
		super("The card ID is missing");
	}
	
	public MissingCardIdException(String message) {
		super(message);
	}
}
