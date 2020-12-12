package model.exceptions;

/**
 * The Class MemoryPracticeRuntimeException.
 * @author Jordi Sellés Enríquez
 */
public class MemoryPracticeRuntimeException extends MemoryPracticeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The message. */
	private String m;

	public MemoryPracticeRuntimeException(String m) {
		super();
	}
	
	@Override
	public String getMessage() {
		return m;
	}
}
