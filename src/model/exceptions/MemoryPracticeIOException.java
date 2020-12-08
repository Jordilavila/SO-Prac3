package model.exceptions;

/**
 * The Class MemoryPracticeIOException.
 */
public class MemoryPracticeIOException extends MemoryPracticeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new memory practice IO exception.
	 *
	 * @param message the message
	 */
	public MemoryPracticeIOException(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return ("Memory Practice I/O Exception: " + this.message);
	}
}
