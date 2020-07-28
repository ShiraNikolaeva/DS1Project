package dsFinalProjectMyCopy;

public class PaymentMadeAlreadyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PaymentMadeAlreadyException() {
		super("Im sorry. A payment has already been ");
	}
}
