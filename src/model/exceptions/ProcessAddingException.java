package model.exceptions;

import model.Process;

/**
 * The Class ProcessAddingException.
 */
public class ProcessAddingException extends ProcessException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new process adding exception.
	 *
	 * @param p the process
	 */
	public ProcessAddingException(Process p) {
		super(p);
		this.message = "";
	}

	/**
	 * Instantiates a new process adding exception.
	 *
	 * @param p the process
	 * @param message the message
	 */
	public ProcessAddingException(Process p, String message) {
		super(p);
		this.message = message;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + "Adding problem. " + this.message;
	}

}
