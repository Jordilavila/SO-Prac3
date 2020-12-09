package model;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.ProcessAddingException;

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
	 * @throws ProcessAddingException 
	 */
	@Override
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
	 * @return the position where we can add it. If we can't, returns -1
	 */
	@Override
	public int checkWhereProcessCanBeAdded(Process p) {
		int avaiableMemCounter = 0;
		boolean cont = true, protectInit = false;
		int initPosFreeMem = 0;
		
		for(int i=0; i<this.getTotalMemory() && cont; i++) {
			if(this.checkFreeMemoryPosition(this.execHashList[i])) {
				avaiableMemCounter++;
				if(!protectInit) {
					initPosFreeMem = i;
					protectInit = false;
				}
			} else if(!this.checkFreeMemoryPosition(this.execHashList[i]) &&  this.checkFreeMemoryPosition(this.execHashList[i - 1])) {
				if(avaiableMemCounter >= p.getNeededMemory()) {
					return initPosFreeMem;
				}
				else return -1;
			}
		}
		throw new RuntimeException("Unexpected");
	}

	/**
	 * Adds the process to exec.
	 *
	 * @param p the p
	 * @return true, if successful
	 * @throws ProcessAddingException 
	 */
	@Override
	public boolean addProcessToExec(Process p) throws ProcessAddingException {
		int checkWhereProcessCanBeAddedReturned = this.checkWhereProcessCanBeAdded(p);
		if(checkWhereProcessCanBeAddedReturned != -1) {
			for(int i = checkWhereProcessCanBeAddedReturned; i<p.getNeededMemory(); i++) {
				this.execHashList[i] = p.hashCode();
			}
			p.changeToExecution();
			if(!this.queue.remove(p)) throw new ProcessAddingException(p, "Process not exists");
			if(this.execProcesses.add(p)) {
				return true;
			}
			throw new ProcessAddingException(p, "Process already in execution");
		}
		return false;
	}
}
