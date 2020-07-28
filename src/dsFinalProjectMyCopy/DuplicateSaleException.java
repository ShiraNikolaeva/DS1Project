package dsFinalProjectMyCopy;

import java.io.Serializable;

public class DuplicateSaleException extends RuntimeException implements Serializable {

	public DuplicateSaleException() {
		super("There is already a duplicate sale that exists");
	}
}
