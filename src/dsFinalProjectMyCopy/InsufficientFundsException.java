package dsFinalProjectMyCopy;

public class InsufficientFundsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException() {
		super("There is not enough funds on this card to make the transaction.");
	}
}
