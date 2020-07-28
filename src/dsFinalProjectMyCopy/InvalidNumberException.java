/*
 * Made by Shira Nikolaeva
 * Date: 12/15/2019
*/

package dsFinalProjectMyCopy;
import java.io.Serializable;

public class InvalidNumberException extends RuntimeException implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public InvalidNumberException() {
		super("number is invalid");
	}

}
