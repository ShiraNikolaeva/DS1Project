package dsFinalProjectMyCopy;
import java.io.Serializable;

public class InvalidDiscountException extends RuntimeException implements Serializable {
	public InvalidDiscountException() {
		super("There is already a discount applied for this month!");
	}
}
