package dsFinalProjectMyCopy;
import java.io.Serializable;

public class ItemNotFoundException extends RuntimeException implements Serializable {


	public ItemNotFoundException() {
		super("The item was not found");
	}
	
	public ItemNotFoundException(String message) {
		super(message);
	}
}
