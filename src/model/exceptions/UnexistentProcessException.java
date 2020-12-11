package model.exceptions;

import model.Process;

public class UnexistentProcessException extends ProcessException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public UnexistentProcessException(Process p) {
		super(p);
	}

	@Override
	public String getMessage() {
		return (super.getMessage() + "process not exists");
	}
}
