package dsFinalProjectMyCopy;

import java.io.Serializable;

public class DetailNotFoundException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DetailNotFoundException() {
		super("This detail was not found");
	}
}
