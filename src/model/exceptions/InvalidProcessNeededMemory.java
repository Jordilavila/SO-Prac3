package model.exceptions;

import model.Process;

public class InvalidProcessNeededMemory extends ProcessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new invalid process needed memory.
	 *
	 * @param p the p
	 */
	public InvalidProcessNeededMemory(Process p) {
		super(p.copy());
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + "the needed memory is incorrect";
	}

}
