package model.exceptions;

import model.Process;

public abstract class ProcessException extends MemoryPracticeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The p. */
	private Process p;
	
	/**
	 * Instantiates a new process exception.
	 *
	 * @param p the p
	 */
	public ProcessException(Process p) {
		this.p = p.copy();
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return "Problem with Process " + p.getProcessName() + "; ";
		
	}
	
}
