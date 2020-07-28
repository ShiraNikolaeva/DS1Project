
package dsFinalProjectMyCopy;
/*
 * Made by Shira Nikolaeva
 * Date: 12/15/2019
*/

import java.io.Serializable;


public class InvalidDataException extends RuntimeException implements Serializable{

	
	public InvalidDataException() {
		super("Invalid Data");
	}
	}
