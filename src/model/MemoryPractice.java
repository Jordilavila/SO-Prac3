package model;

import java.util.Objects;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.InvalidProcessorTypeException;
import model.exceptions.MemoryPracticeIOException;
import model.exceptions.ProcessAddingException;
import model.io.IProcessorLoader;
import model.io.ProcessorLoaderFile;

/**
 * The Class MemoryPractice.
 * @author Jordi Sellés Enríquez
 */
public class MemoryPractice {
	
	/** The processor. */
	private Processor processor;
	
	/**
	 * Instantiates a new memory practice.
	 *
	 * @param type the type
	 * @param totalMemory the total memory
	 * @param filePath the file path
	 * @throws InvalidProcessorTypeException the invalid processor type exception
	 * @throws MemoryPracticeIOException the memory practice IO exception
	 * @throws InvalidProcessNeededMemory the invalid process needed memory
	 * @throws NumberFormatException the number format exception
	 * @throws ProcessAddingException the process adding exception
	 */
	public MemoryPractice(String type, int totalMemory, String filePath) throws InvalidProcessorTypeException, MemoryPracticeIOException, InvalidProcessNeededMemory, NumberFormatException, ProcessAddingException {
		Objects.requireNonNull(type);
		Objects.requireNonNull(filePath);
		
		if(type.equals("BEST")) processor = new ProcessorBest(totalMemory);
		else if(type.equals("WORST")) processor = new ProcessorWorst(totalMemory);
		else throw new InvalidProcessorTypeException("The type of processor is not correct. It was " + type);
		
		IProcessorLoader ip = new ProcessorLoaderFile(filePath);
		ip.loadProcesses(processor);
	}
	
	public void run() {
		
	}
}
