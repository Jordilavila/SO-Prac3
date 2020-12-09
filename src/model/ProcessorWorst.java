package model;

import model.exceptions.ProcessAddingException;

/**
 * The Class ProcessorWorst.
 * @author Jordi Sell�s Enr�quez
 */
public class ProcessorWorst extends Processor {

	public ProcessorWorst(int totalMemory) {
		super(totalMemory);
	}

	@Override
	public boolean moveProcessFromQueueToExec(Process p) throws ProcessAddingException {
		// TODO Esbozo de m�todo generado autom�ticamente
		return false;
	}

	@Override
	public int checkWhereProcessCanBeAdded(Process p) {
		// TODO Esbozo de m�todo generado autom�ticamente
		return -1;
	}

}
