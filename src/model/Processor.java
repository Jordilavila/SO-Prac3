package model;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.ProcessAddingException;

public abstract class Processor {
	protected TreeSet<Process> queue;
	protected int[] execHashList;
	protected Set<Process> execProcesses;
	protected int totalMemory;
	protected final static int MINIMAL_PROCESSOR_MEMORY_SIZE = 1024;
	protected final static int MINIMAL_PROCESS_MEMORY_SIZE = 100;
	protected final static int FREE_MEMORY_SPACE_IDENDIFYER = -1;
	
	
	/**
	 * Instantiates a new processor.
	 *
	 * @param totalMemory the total memory
	 */
	public Processor(int totalMemory) {
		if(totalMemory <= Processor.MINIMAL_PROCESSOR_MEMORY_SIZE) throw new NumberFormatException("totalMemory have to be greater than " + Processor.MINIMAL_PROCESSOR_MEMORY_SIZE);
		
		this.totalMemory = totalMemory;
		this.execHashList = new int[totalMemory];
		this.queue = new TreeSet<Process>();
		this.execProcesses = new HashSet<Process>();
		for(int i=0; i<this.totalMemory; i++) this.execHashList[i] = Processor.FREE_MEMORY_SPACE_IDENDIFYER;
	}
	
	public abstract boolean addProcessToExec(Process p) throws ProcessAddingException;
	
	/**
	 * Adds the process to queue.
	 *
	 * @param p the process
	 * @return true, if successful
	 * @throws InvalidProcessNeededMemory 
	 * @throws ProcessAddingException 
	 */
	public abstract boolean addProcessToQueue(Process p) throws InvalidProcessNeededMemory, ProcessAddingException;
	
	/**
	 * Check where process can be added.
	 *
	 * @param p the process
	 * @return the position where we can add it, if successful. Otherwise, -1.
	 */
	public abstract int checkWhereProcessCanBeAdded(Process p);
	
	/**
	 * Clean all memory.
	 */
	public void cleanAllMemory() { for(int i=0; i<this.totalMemory; i++) this.execHashList[i] = Processor.FREE_MEMORY_SPACE_IDENDIFYER; }
	
	public void deleteProcessFromExecution(int processHash) {
		
	}
	
	/**
	 * Check free memory position.
	 *
	 * @param position the position
	 * @return true, if successful
	 */
	public boolean checkFreeMemoryPosition(int position) {
		return ((position == Processor.FREE_MEMORY_SPACE_IDENDIFYER) ? true : false);
	}
	
	/**
	 * Gets the total memory.
	 *
	 * @return the total memory
	 */
	public int getTotalMemory() { return this.totalMemory; }
	
	
}
