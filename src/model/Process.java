package model;

import java.util.Objects;

/**
 * The Class Process.
 * @author Jordi Sellés Enríquez
 */
public class Process {
	
	/** The process name. */
	private String processName;
	
	/** The arrival time. */
	private int arrivalTime;
	
	/** The execution time. */
	private int executionTime;
	
	/** The internal counter. */
	private int internalCounter;
	
	/**
	 * Instantiates a new process.
	 * 
	 * All params have to be positive. If not, it will throw an Exception
	 *
	 * @param processName the process name
	 * @param arrivalTime the arrival time
	 * @param executionTime the execution time
	 * @param internalCounter the internal counter
	 */
	public Process(String processName, int arrivalTime, int executionTime, int internalCounter) {
		Objects.requireNonNull(processName);
		if(arrivalTime < 0) throw new NumberFormatException("arrivalTime can't be negative. ");
		if(executionTime < 0) throw new NumberFormatException("executionTime can't be negative. ");
		if(internalCounter < 0) throw new NumberFormatException("internalCounter can't be negative. ");
		
		this.arrivalTime = arrivalTime;
		this.executionTime = executionTime;
		this.internalCounter = internalCounter;
		this.processName = processName;
	}

	/**
	 * Gets the internal counter.
	 *
	 * @return the internal counter
	 */
	public int getInternalCounter() { return this.internalCounter; }
	
	/**
	 * Gets the arrival time.
	 *
	 * @return the arrival time
	 */
	public int getArrivalTime() { return this.arrivalTime; }

	/**
	 * Gets the execution time.
	 *
	 * @return the execution time
	 */
	public int getExecutionTime() { return executionTime; }

	/**
	 * Gets the process name.
	 *
	 * @return the process name
	 */
	public String getProcessName() { return processName; }
}
