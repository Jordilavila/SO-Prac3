package model.io;

import model.Processor;
import model.exceptions.MemoryPracticeIOException;

/**
 * The Interface IProcessorLoader.
 * @author Jordi Sellés Enríquez
 */
public interface IProcessorLoader {
	
	/**
	 * Load processes.
	 *
	 * @param p the processor
	 * @throws MemoryPracticeIOException the memory practice IO exception
	 */
	public void loadProcesses(Processor p) throws MemoryPracticeIOException;
}
