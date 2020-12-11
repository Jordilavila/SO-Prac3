package model;

import java.util.Objects;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.InvalidProcessorTypeException;
import model.exceptions.MemoryPracticeIOException;
import model.exceptions.ProcessAddingException;
import model.exceptions.ProcessExecutionTimeExceeded;
import model.exceptions.UnexistentProcessException;
import model.io.IProcessorLoader;
import model.io.ProcessorLoaderFile;

/**
 * The Class MemoryPractice.
 * @author Jordi Sellés Enríquez
 * 
 * This object works as a memory manager
 */
public class MemoryPractice {
	
	/** The processor. */
	private Processor processor;
	
	/** The file path. */
	private String filePath;
	
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
	public MemoryPractice(String type, int totalMemory, String filePath) throws InvalidProcessorTypeException {
		Objects.requireNonNull(type);
		Objects.requireNonNull(filePath);
		
		if(type.equals("BEST")) processor = new ProcessorBest(totalMemory);
		else if(type.equals("WORST")) processor = new ProcessorWorst(totalMemory);
		else throw new InvalidProcessorTypeException("The type of processor is not correct. It was " + type);
		
		this.filePath = filePath;
	}
	
	/*
	 * Esto significa que me toca implementar dos métodos nuevos en Processor, que serán:
	 *  - incrementProcessesCounter()
	 *  - killProcess()
	 */
	
	/**
	 * Run.
	 * 
	 * Once the object that controls the program (this) is built, we will call MemoryPractice.run() method.
	 * This method is which will load the processes to the Processor.queue and will run them. If can't move them, will capture the exception and will let the iteration pass and will try to 
	 * do it in the next iteration. Anyway, in each iteration, the processor will increments the internal counter of each process that be in execution.
	 * Finally, if the internal counter of the process is equals to executionTime, the process will be killed.
	 * @throws MemoryPracticeIOException 
	 * @throws ProcessAddingException 
	 * @throws NumberFormatException 
	 * @throws InvalidProcessNeededMemory 
	 * @throws UnexistentProcessException 
	 */
	public void run() throws MemoryPracticeIOException, InvalidProcessNeededMemory, NumberFormatException, ProcessAddingException, UnexistentProcessException {
		boolean play = true;
		
		IProcessorLoader ip = new ProcessorLoaderFile(this.filePath);
		ip.loadProcesses(processor);
		
		while(play) {
			for(Process it : this.getProcessor().getQueue()) {
				try {
					this.getProcessor().moveProcessFromQueueToExec(it);
				} catch (ProcessAddingException e) {
					// Process can't be added because memory is full
				}
			}
			this.getProcessor().incrementProcessesCounter();
			for(Process it : this.getProcessor().getExecProcesses()) {
				try {
					if(this.getProcessor().askIfProcessIsTerminated(it))
						this.getProcessor().killProcess(it);
				} catch (ProcessExecutionTimeExceeded e) {
					this.getProcessor().killProcess(it);
				}
			}
			if(this.getProcessor().isMemoryClean()) play = false;
			
			// View SHOW
			
		} // WHILE END
	}
	
	/**
	 * Gets the processor.
	 *
	 * @return the processor
	 */
	private Processor getProcessor() {
		return this.processor;
	}
}
