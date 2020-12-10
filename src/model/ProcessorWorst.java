package model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The Class ProcessorWorst.
 * @author Jordi Sellés Enríquez
 */
public class ProcessorWorst extends Processor {

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
		Map<Integer, Integer> emptySpaces = this.lookForEmptySpaces();
		if(emptySpaces.isEmpty()) { return 0; }
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
		if(lastSize == this.totalMemory) { return -1; }
		return returnKey;
	}

}
