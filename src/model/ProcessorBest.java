package model;

import java.util.Objects;

import model.exceptions.ProcessAddingException;

/**
 * The Class ProcessorBest.
 * @author Jordi Sellés Enríquez
 */
public class ProcessorBest extends Processor {

	/**
	 * Instantiates a new ProcessorBest.
	 *
	 * @param totalMemory the total memory
	 */
	public ProcessorBest(int totalMemory) {
		super(totalMemory);
	}

	/*
	 * Unos apuntes...
	 * Este objeto debe de añadir procesos en el hueco más ajustado al tamaño del proceso.
	 * Por esto, me va a tocar revisar el método siguiente.
	 */

	@Override
	public int checkWhereProcessCanBeAdded(Process p) {
		Objects.requireNonNull(p);
		int avaiableMemCounter = 0;
		boolean protectInit = false;
		int initPosFreeMem = 0;
		int lastInitPosFreeMem = -1;
		int lastSize = 0;

		if(this.isMemoryClean()) {
			return 0;
		}
		

		for(int i=0; i<this.getTotalMemory(); i++) {
			if(i == 500) {
				int x = 0;
			}
			// Fallo por aqui
			if(this.checkFreeMemoryPosition(this.execHashList[i])) {
				avaiableMemCounter++;
				if(!protectInit) {
					initPosFreeMem = i;
					protectInit = true;
				}

			} 
			if(i > 0) {
				if(!this.checkFreeMemoryPosition(this.execHashList[i]) &&  this.checkFreeMemoryPosition(this.execHashList[i - 1])) {
					if(avaiableMemCounter >= p.getNeededMemory()) {
						protectInit = false;
						if(lastSize > avaiableMemCounter) {
							lastSize = avaiableMemCounter;
							lastInitPosFreeMem = initPosFreeMem;
						}
					}
				} 
			}
		}
		return lastInitPosFreeMem;
	}
	

	/**
	 * Adds the process to exec.
	 *
	 * @param p the p
	 * @return true, if successful
	 * @throws ProcessAddingException 
	 */
	@Override
	public boolean moveProcessFromQueueToExec(Process p) throws ProcessAddingException {
		Objects.requireNonNull(p);
		int checkWhereProcessCanBeAddedReturned = this.checkWhereProcessCanBeAdded(p);
		if(checkWhereProcessCanBeAddedReturned != -1) {
			for(int i = checkWhereProcessCanBeAddedReturned; i<p.getNeededMemory(); i++) {
				this.execHashList[i] = p.hashCode();
			}
			p.setInitialPos(checkWhereProcessCanBeAddedReturned);
			if(!this.queue.remove(p)) throw new ProcessAddingException(p, "Process not exists");
			if(this.execProcesses.add(p)) {
				return true;
			}
			throw new ProcessAddingException(p, "Process already in execution");
		}
		return false;
	}
}
