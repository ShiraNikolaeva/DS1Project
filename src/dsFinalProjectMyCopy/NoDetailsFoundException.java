package dsFinalProjectMyCopy;
import java.io.Serializable;

public class NoDetailsFoundException extends RuntimeException implements Serializable {
	public NoDetailsFoundException() {
		super("No details found!");
	}
}
