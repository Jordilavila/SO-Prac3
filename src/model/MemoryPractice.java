package model;

import java.util.Objects;
import java.util.Set;

import model.exceptions.InvalidProcessorTypeException;
import model.exceptions.MemoryPracticeIOException;
import model.exceptions.ProcessAddingException;
import model.exceptions.UnexistentProcessException;
import model.io.IProcessorLoader;
import model.io.IViewer;
import model.io.ProcessorLoaderFile;
import model.io.ViewerConsole;

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
	 * Once the object that controls the program (this) is built, we will call MemoryPractice.run() method.
	 * This method is which will load the processes to the Processor.queue and will run them. If can't move them, will capture the exception and will let the iteration pass and will try to 
	 * do it in the next iteration. Anyway, in each iteration, the processor will increments the internal counter of each process that be in execution.
	 * Finally, if the internal counter of the process is equals to executionTime, the process will be killed.
	 * @throws MemoryPracticeIOException
	 * @throws UnexistentProcessException 
	 */
	public void run(IViewer viewer) throws UnexistentProcessException, MemoryPracticeIOException {
		boolean play = true;
		this.start();
		viewer.show();
		while(play) {
			this.incrementCounter();
			Set<Process> queue = this.getProcessor().getCopyOfQueue();
			for(Process it : queue) {
				try {
					this.getProcessor().moveProcessFromQueueToExec(it);
				} catch (ProcessAddingException e) {
					// Process can't be added because memory is full
				}
			}
			this.getProcessor().incrementProcessesCounter();
			this.getProcessor().killTerminatedProcesses(); // Supongo que el problema estará por aquí
			
			// Se crea un bucle infinito porque no se están matando los procesos
			
			if(this.isFinalized()) play = false;
			viewer.show();
		} // WHILE END
		
		if(viewer instanceof ViewerConsole) viewer.show();
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
	 */
	private Processor getProcessor() {
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
