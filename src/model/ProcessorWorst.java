package model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class implements a Processor with worst-fit algorithm.
 * @author Jordi Sellés Enríquez
 */
public class ProcessorWorst extends Processor {

	/**
	 * Instantiates a new processor worst.
	 *
	 * @param totalMemory the total memory
	 */
	public ProcessorWorst(int totalMemory) {
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
		
		int lastSize = 0;
		int returnKey = -1;
		Set<Integer> setKeys = emptySpaces.keySet();
		
		for(Integer it : setKeys) {
			if(emptySpaces.containsKey(it)) {
				int actualSize = emptySpaces.get(it);
				if(actualSize >= p.getNeededMemory()) {
					if(lastSize < actualSize) {
						lastSize = actualSize;
						returnKey = it.intValue();
					}
				}
			}
		}
		return returnKey;
	}

}
