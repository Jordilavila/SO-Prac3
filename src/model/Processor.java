package model;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.ProcessAddingException;

/**
 * The Class Processor.
 * @author Jordi Sellés Enríquez
 */
public abstract class Processor {
	
	/** The queue. */
	protected Set<Process> queue;
	
	/** The exec hash list. */
	protected int[] execHashList;
	
	/** The exec processes. */
	protected Set<Process> execProcesses;
	
	/** The total memory. */
	protected int totalMemory;
	
	/** The Constant MINIMAL_PROCESSOR_MEMORY_SIZE. */
	protected final static int MINIMAL_PROCESSOR_MEMORY_SIZE = 1024;
	
	/** The Constant MINIMAL_PROCESS_MEMORY_SIZE. */
	protected final static int MINIMAL_PROCESS_MEMORY_SIZE = 100;
	
	/** The Constant FREE_MEMORY_SPACE_IDENDIFYER. */
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
		this.queue = new LinkedHashSet<Process>();
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
	public boolean addProcessToQueue(Process p) throws InvalidProcessNeededMemory, ProcessAddingException {
		if(p.getNeededMemory() < Processor.MINIMAL_PROCESS_MEMORY_SIZE || p.getNeededMemory() > this.getTotalMemory()) throw new InvalidProcessNeededMemory(p);
		if(this.queue.add(p)) {
			return true;
		}
		throw new ProcessAddingException(p, "Process already exists");
	}
	
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
