package model;

import model.exceptions.ProcessAddingException;

/**
 * The Class ProcessorWorst.
 * @author Jordi Sellés Enríquez
 */
public class ProcessorWorst extends Processor {

	public ProcessorWorst(int totalMemory) {
		super(totalMemory);
	}

	@Override
	public boolean moveProcessFromQueueToExec(Process p) throws ProcessAddingException {
		// TODO Esbozo de método generado automáticamente
		return false;
	}

	@Override
	public int checkWhereProcessCanBeAdded(Process p) {
		// TODO Esbozo de método generado automáticamente
		return -1;
	}

}
