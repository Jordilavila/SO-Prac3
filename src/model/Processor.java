package model;

import java.util.TreeSet;

import model.exceptions.InvalidProcessNeededMemory;

public abstract class Processor {
	protected TreeSet<Process> queue;
	protected int[] execution;
	protected int totalMemory;
	protected int occupiedMemory;
	protected final static int MINIMAL_MEMORY_SIZE = 1024;
	protected final static int FREE_MEMORY_SPACE_IDENDIFYER = -1;
	
	
	/**
	 * Instantiates a new processor.
	 *
	 * @param totalMemory the total memory
	 */
	public Processor(int totalMemory) {
		if(totalMemory <= Processor.MINIMAL_MEMORY_SIZE) throw new NumberFormatException("totalMemory have to be greater than " + Processor.MINIMAL_MEMORY_SIZE);
		
		this.totalMemory = totalMemory;
		this.occupiedMemory = 0;
		this.execution = new int[totalMemory];
		for(int i=0; i<this.totalMemory; i++) this.execution[i] = Processor.FREE_MEMORY_SPACE_IDENDIFYER;
	}
	
	/**
	 * Adds the process to queue.
	 *
	 * @param p the process
	 * @return true, if successful
	 * @throws InvalidProcessNeededMemory 
	 */
	public abstract boolean addProcessToQueue(Process p) throws InvalidProcessNeededMemory;
	
	/**
	 * Check if process can be added.
	 *
	 * @param p the p
	 * @return true, if successful
	 */
	public abstract boolean checkIfProcessCanBeAdded(Process p);
	
	/**
	 * Clean all memory.
	 */
	public void cleanAllMemory() { for(int i=0; i<this.totalMemory; i++) this.execution[i] = Processor.FREE_MEMORY_SPACE_IDENDIFYER; }
	
	public void deleteProcessFromExecution(int processHash) {
		
	}
	
	/**
	 * Gets the total memory.
	 *
	 * @return the total memory
	 */
	public int getTotalMemory() { return this.totalMemory; }
	
	
}
