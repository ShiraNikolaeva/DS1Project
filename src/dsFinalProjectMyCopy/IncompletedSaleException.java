package dsFinalProjectMyCopy;
import java.io.Serializable;

public class IncompletedSaleException extends RuntimeException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncompletedSaleException() {
		super("The sale is incomplete");
	}
}
