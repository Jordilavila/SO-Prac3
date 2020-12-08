package model.io;

import model.Processor;
import model.exceptions.InvalidProcessNeededMemory;
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
	 * @throws MemoryPracticeIOException 
	 * @throws NumberFormatException 
	 * @throws InvalidProcessNeededMemory 
	 */
	public void loadProcesses(Processor p) throws MemoryPracticeIOException, InvalidProcessNeededMemory, NumberFormatException;
}
