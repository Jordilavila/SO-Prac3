package model;

import java.util.HashSet;
import java.util.Set;

public class Processor {
	private Set<Process> queue;
	private Set<Process> execution;
	private int totalMemory;
	private final static int MINIMAL_MEMORY_SIZE = 1024;
	
	
	public Processor(int totalMemory) {
		if(totalMemory <= Processor.MINIMAL_MEMORY_SIZE) throw new NumberFormatException("totalMemory have to be greater than " + Processor.MINIMAL_MEMORY_SIZE);
		
		this.totalMemory = totalMemory;
		queue = new HashSet<Process>();
		execution = new HashSet<Process>();
	}
	
	
}
