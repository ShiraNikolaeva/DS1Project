package dsFinalProjectMyCopy;
import java.io.Serializable;

public class InvalidUPCException extends RuntimeException implements Serializable{
	public InvalidUPCException() {
		super("Invalid UPC!");
	}

}
