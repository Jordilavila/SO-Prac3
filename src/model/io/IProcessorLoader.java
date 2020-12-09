package model.io;

import model.Processor;
import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.MemoryPracticeIOException;
import model.exceptions.ProcessAddingException;

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
	 * @throws InvalidProcessNeededMemory the invalid process needed memory
	 * @throws NumberFormatException the number format exception
	 * @throws ProcessAddingException the process adding exception
	 */
	public void loadProcesses(Processor p) throws MemoryPracticeIOException, InvalidProcessNeededMemory, NumberFormatException, ProcessAddingException;
}
