package dsFinalProjectMyCopy;

import java.io.Serializable;

public class WrongEBTAmountException extends Exception implements Serializable {
	public WrongEBTAmountException() {
		super("Wrong EBT Amount!");
	}
	public WrongEBTAmountException(String msg) {
		super(msg);
	}
}
