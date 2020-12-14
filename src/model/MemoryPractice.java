package model;

import java.util.ArrayList;
import java.util.Objects;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.InvalidProcessorTypeException;
import model.exceptions.MemoryPracticeIOException;
import model.exceptions.MemoryPracticeRuntimeException;
import model.exceptions.ProcessAddingException;
import model.exceptions.ProcessExecutionTimeExceeded;
import model.io.IProcessorLoader;
import model.io.IViewer;
import model.io.ProcessorLoaderFile;

/**
 * This object works as a memory manager. Once the object that controls the program is built, we will call MemoryPractice.run() method.
 * This method is the one that will load the processes to the Processor.queue and will run them. If it can't move them, it will capture the exception
 * and let the iteration pass and it will try to do it in the next iteration.
 * Anyway, in each iteration the processor will increment the internal counter of each processes that are in execution.
 * Finally, if the internal counter of the process is equals to executionTime, the process will be killed.
 * 
 * @author Jordi Sellés Enríquez
 */
public class MemoryPractice {
	
	/** The processor. */
	private Processor processor;
	
	/** The file path. */
	private String filePath;
	
	/** The counter of iterations. */
	private int counterOfIterations;
	
	/**
	 * Instantiates a new memory practice.
	 *
	 * @param type the type
	 * @param totalMemory the total memory
	 * @param filePath the file path
	 * @throws InvalidProcessorTypeException the invalid processor type exception
	 */
	public MemoryPractice(String type, int totalMemory, String filePath) throws InvalidProcessorTypeException {
		Objects.requireNonNull(type);
		Objects.requireNonNull(filePath);
		
		if(type.equals("BEST")) processor = new ProcessorBest(totalMemory);
		else if(type.equals("WORST")) processor = new ProcessorWorst(totalMemory);
		else throw new InvalidProcessorTypeException("The type of processor is not correct. It was " + type);
		
		this.filePath = filePath;
		this.counterOfIterations = 0;
	}
	
	public MemoryPractice(Processor processor, String filePath) {
		Objects.requireNonNull(processor);
		Objects.requireNonNull(filePath);
		
		this.filePath = filePath;
		this.processor = processor;
	}
	
	/**
	 * Start.
	 *
	 * @throws MemoryPracticeIOException the memory practice IO exception
	 */
	private void start() throws MemoryPracticeIOException {
		IProcessorLoader ip = new ProcessorLoaderFile(this.filePath);
		ip.loadProcesses(processor);
	}
	
	/**
	 * Run.
	 * 
	 * Once the object that controls the program is built, we will call MemoryPractice.run() method.
	 * This method is the one that will load the processes to the Processor.queue and will run them. If it can't move them, it will capture the exception
	 * and let the iteration pass and it will try to do it in the next iteration.
	 * Anyway, in each iteration the processor will increment the internal counter of each processes that are in execution.
	 * Finally, if the internal counter of the process is equals to executionTime, the process will be killed.
	 *
	 * @param viewer the viewer
	 * @throws MemoryPracticeIOException the memory practice IO exception
	 * @throws MemoryPracticeRuntimeException the memory practice runtime exception
	 * @see moveProcessFromExecToKilled
	 * @see moveProcessFromQueueToExec
	 */
	public void run(IViewer viewer) throws MemoryPracticeIOException, MemoryPracticeRuntimeException {
		boolean play = true;
		this.start();
		viewer.show();
		while(play) {
			this.incrementCounter();
			
			// KILLING PROCESSES
			ArrayList<Process> finalized = this.processor.getCopyOfExecProcesses();
			for(Process it : finalized) {
				try {
					if(it.isFinalized()) {
						try {
							this.processor.moveProcessFromExecToKilled(it);
						} catch (InvalidProcessNeededMemory | ProcessAddingException e) {
							throw new MemoryPracticeRuntimeException(e.getMessage());
						}
					}
				} catch (ProcessExecutionTimeExceeded e1) {
					try {
						this.processor.moveProcessFromExecToKilled(it);
					} catch (InvalidProcessNeededMemory | ProcessAddingException e) {
						throw new MemoryPracticeRuntimeException(e.getMessage());
					}
				}
				
			}
			
			// Adding from queue:
			if(!this.processor.getQueueAsArrayList().isEmpty()) {
				Process firstInQueue = this.getProcessor().getQueueAsArrayList().get(0);
				try {
					this.processor.moveProcessFromQueueToExec(firstInQueue);
				} catch (ProcessAddingException e) {
					throw new MemoryPracticeRuntimeException(e.getMessage());
				}
			}
			
			if(this.isFinalized()) play = false;
			this.processor.incrementProcessesCounter();
			viewer.show();
		} // WHILE END
		viewer.close();
	}
	
	/**
	 * Checks if is finalized.
	 *
	 * @return true, if is finalized
	 */
	public boolean isFinalized() {
		if(this.getProcessor().isMemoryClean() && this.getProcessor().getQueue().isEmpty()) {
			return true;
		}
		return false;
	}
	/**
	 * Increment iteration counter.
	 */
	private void incrementCounter() { this.counterOfIterations++; }
	
	/**
	 * Gets the processor.
	 *
	 * @return the processor
	 * @see Processor
	 */
	public Processor getProcessor() {
		return this.processor;
	}
	
	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	private int getCounter() { return this.counterOfIterations; }
	
	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	private String getFilePath() { return this.filePath; }
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String ret = "";
		ret += "===== MEMORY MANAGER =====\n";
		ret += "=== DATA ===\n";
		ret += "Iterations: " + this.getCounter(); ret += "\n";
		ret += "File Path: " + this.getFilePath(); ret += "\n";
		ret += this.getProcessor().toString();
		return ret;
	}
}
