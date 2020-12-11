package model.exceptions;

/**
 * The Class InvalidProcessorTypeException.
 * @author Jordi Sellés Enríquez
 */
public class InvalidProcessorTypeException extends MemoryPracticeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String m;
	
	public InvalidProcessorTypeException(String message) {
		this.m = message;
	}
	
	@Override
	public String getMessage() {
		return m;
	}
}
