package dsFinalProjectMyCopy;
import java.io.Serializable;

public class SalesDetailNotFoundException extends RuntimeException implements Serializable {
	public SalesDetailNotFoundException() {
		super("Sales Detail is not found!");
	}
}
