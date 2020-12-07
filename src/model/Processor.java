package model;

import java.util.TreeSet;

import model.exceptions.InvalidProcessNeededMemory;

public class Processor {
	private TreeSet<Process> queue;
	private int[] execution;
	private int totalMemory;
	private int occupiedMemory;
	private final static int MINIMAL_MEMORY_SIZE = 1024;
	
	
	public Processor(int totalMemory) {
		if(totalMemory <= Processor.MINIMAL_MEMORY_SIZE) throw new NumberFormatException("totalMemory have to be greater than " + Processor.MINIMAL_MEMORY_SIZE);
		
		this.totalMemory = totalMemory;
		this.occupiedMemory = 0;
		this.execution = new int[totalMemory];
	}
	
	/**
	 * Adds the process to queue.
	 *
	 * @param p the process
	 * @return true, if successful
	 * @throws InvalidProcessNeededMemory 
	 */
	public boolean addProcessToQueue(Process p) throws InvalidProcessNeededMemory {
		if(p.getNeededMemory() < Processor.MINIMAL_MEMORY_SIZE || p.getNeededMemory() > this.getTotalMemory()) throw new InvalidProcessNeededMemory(p);
		
		// Check if process can be added:
		int avaiableMemCounter = 0;
			// I'M HERE
		
		this.occupiedMemory = this.occupiedMemory + p.getNeededMemory();
		return this.queue.add(p);
	}
	
	/**
	 * Gets the total memory.
	 *
	 * @return the total memory
	 */
	public int getTotalMemory() { return this.totalMemory; }
	
	
}
