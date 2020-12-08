package model;

import model.exceptions.InvalidProcessNeededMemory;

public class ProcessorBest extends Processor {

	public ProcessorBest(int totalMemory) {
		super(totalMemory);
	}
	
	/**
	 * Adds the process to queue.
	 *
	 * @param p the process
	 * @return true, if successful
	 * @throws InvalidProcessNeededMemory 
	 */
	@Override
	public boolean addProcessToQueue(Process p) throws InvalidProcessNeededMemory {
		if(p.getNeededMemory() < Processor.MINIMAL_MEMORY_SIZE || p.getNeededMemory() > this.getTotalMemory()) throw new InvalidProcessNeededMemory(p);

		// Check if process can be added:
		int avaiableMemCounter = 0;
		for(int i=0; i<this.getTotalMemory(); i++) {

		}
		// I'M HERE

		this.occupiedMemory = this.occupiedMemory + p.getNeededMemory();
		return this.queue.add(p);
	}

	@Override
	public boolean checkIfProcessCanBeAdded(Process p) {
		// Check if process can be added:
		int avaiableMemCounter = 0;
		boolean cont = true;
		for(int i=0; i<this.getTotalMemory() && cont; i++) {
			if(this.occupiedMemory[i] == Processor.FREE_MEMORY_SPACE_IDENDIFYER) {
				// I'M HERE
			}
		}
		// I'M HERE
		return false;
	}
}
