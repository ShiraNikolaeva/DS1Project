package dsFinalProjectMyCopy;
import java.io.Serializable;

public class NoDiscountFoundException extends RuntimeException implements Serializable{

	public NoDiscountFoundException() {
		super("There is no discount found that includes the specified date");
	}
}
