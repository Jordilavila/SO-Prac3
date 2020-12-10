package model;

import java.util.Objects;
import java.util.Set;
import java.util.Map;

import model.exceptions.ProcessAddingException;

/**
 * The Class ProcessorBest.
 * @author Jordi Sellés Enríquez
 */
public class ProcessorBest extends Processor {
	// https://1984.lsi.us.es/wiki-ssoo/index.php/Implementaci%C3%B3n_de_ajustes_en_Java

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
		Map<Integer, Integer> emptySpaces = this.lookForEmptySpaces();
		
		if(emptySpaces.isEmpty()) { return 0; }
		
		int lastSize = this.totalMemory;
		int returnKey = -1;
		Set<Integer> setKeys = emptySpaces.keySet();
		
		for(Integer it : setKeys) {
			if(emptySpaces.containsKey(it)) {
				int actualSize = emptySpaces.get(it);
				if(actualSize >= p.getNeededMemory()) {
					if(lastSize > actualSize) {
						lastSize = actualSize;
						returnKey = it.intValue();
					}
				}
			}
		}
		
		for(int i = 1; i<emptySpaces.size(); i++) {
			
		}
		
		if(lastSize == this.totalMemory) { return -1; }
		
		return returnKey;
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
			for(int i = checkWhereProcessCanBeAddedReturned; i<(p.getNeededMemory() + checkWhereProcessCanBeAddedReturned); i++) {
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
