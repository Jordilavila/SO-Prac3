package model.exceptions;

import model.Process;

public class ProcessExecutionTimeExceeded extends ProcessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new process execution time exceeded.
	 *
	 * @param p the process
	 */
	public ProcessExecutionTimeExceeded(Process p) {
		super(p);
	}
	
	@Override
	public String getMessage() {
		return (super.getMessage() + "the process exceeded its execution time.");
	}
}
