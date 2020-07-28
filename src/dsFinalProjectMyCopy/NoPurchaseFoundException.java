package dsFinalProjectMyCopy;
import java.io.Serializable;

public class NoPurchaseFoundException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoPurchaseFoundException() {
		super("No Purchase Found!");
	}
}
