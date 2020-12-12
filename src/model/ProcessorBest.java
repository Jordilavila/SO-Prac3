package model;

import java.util.Objects;
import java.util.Set;
import java.util.Map;

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
	
	/**
	 * Check where process can be added.
	 *
	 * @param p the process
	 * @return the int
	 */
	@Override
	protected int checkWhereProcessCanBeAdded(Process p) {
		Objects.requireNonNull(p);
		if(this.askIfMemoryIsFull()) return -1;
		
		Map<Integer, Integer> emptySpaces = this.lookForEmptySpaces();
		
		int lastSize = this.totalMemory + 1;
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
		return returnKey;
	}
}
