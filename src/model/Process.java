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
	
	/** The needed memory. */
	private int neededMemory;
	
	/** The initial pos. */
	private int initialPos;
	
	/** The in execution. */
	private boolean inExecution;
	
	/** The Constant xAxisNameStringSeparator. */
	private final static String xAxisNameStringSeparator = "==================================================";
	
	/** The Constant xAxisStringSeparator. */
	private final static String xAxisStringSeparator = "__________________________________________________";
	
	/** The Constant endl. */
	private final static String endl = "\n";
	
	/**
	 * Instantiates a new process.
	 *
	 * @param processName the process name
	 * @param arrivalTime the arrival time
	 * @param executionTime the execution time
	 * @param neededMemory the needed memory
	 */
	public Process(String processName, int arrivalTime, int executionTime, int neededMemory) {
		Objects.requireNonNull(processName);
		if(arrivalTime < 0) throw new NumberFormatException("arrivalTime can't be negative. ");
		if(executionTime < 0) throw new NumberFormatException("executionTime can't be negative. ");
		if(neededMemory <= 0) throw new NumberFormatException("neededMemory have to be greater than 0");
		
		this.arrivalTime = arrivalTime;
		this.executionTime = executionTime;
		this.internalCounter = 0;
		this.processName = processName;
		this.neededMemory = neededMemory;
		this.inExecution = false;
		this.initialPos = -1;
	}
	
	/**
	 * Instantiates a new process.
	 *
	 * @param p the process
	 */
	protected Process(Process p) {
		this.arrivalTime = p.getArrivalTime();
		this.executionTime = p.getExecutionTime();
		this.internalCounter = p.getInternalCounter();
		this.neededMemory = p.getNeededMemory();
		this.processName = p.getProcessName();
	}
	
	/**
	 * Copy.
	 *
	 * @return the process
	 */
	public Process copy() { return new Process(this); }
	
	/**
	 * Increment internal counter.
	 */
	public void incrementInternalCounter() { this.internalCounter++; }

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

	/**
	 * Gets the needed memory.
	 *
	 * @return the needed memory
	 */
	public int getNeededMemory() { return neededMemory; }
	
	/**
	 * Gets the in execution.
	 *
	 * @return the in execution
	 */
	public boolean getInExecution() { return this.inExecution; }
	
	/**
	 * Change to execution.
	 */
	private void changeToExecution() { this.inExecution = true; }
	
	/**
	 * Quit from execution.
	 */
	public void quitFromExecution() { this.inExecution = false; }
	
	/**
	 * Info.
	 *
	 * @return A string with detailed process info
	 */
	public String info() {
		String ret = "";
		ret += Process.xAxisNameStringSeparator; ret += Process.endl;
		ret += "Process name: " + this.getProcessName(); ret += Process.endl;
		ret += Process.xAxisNameStringSeparator; ret += Process.endl;
		ret += "Arrival time: " + this.getArrivalTime(); ret += Process.endl;
		ret += "Execution time: " + this.getExecutionTime(); ret += Process.endl;
		ret += "Internal counter: " + this.getInternalCounter(); ret += Process.endl;
		ret += "Needed memory: " + this.getNeededMemory(); ret += Process.endl;
		ret += "Status: " + ((this.getInExecution()) ? "In execution" : "Waiting"); ret += Process.endl;
		ret += Process.xAxisStringSeparator;
		return ret;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return ("[" + this.getInitialPos() + " " + this.getProcessName() + " " + (this.getNeededMemory() - 1) + "]");
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arrivalTime;
		result = prime * result + executionTime;
		result = prime * result + internalCounter;
		result = prime * result + neededMemory;
		result = prime * result + ((processName == null) ? 0 : processName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Process other = (Process) obj;
		if (arrivalTime != other.arrivalTime)
			return false;
		if (executionTime != other.executionTime)
			return false;
		if (internalCounter != other.internalCounter)
			return false;
		if (neededMemory != other.neededMemory)
			return false;
		if (processName == null) {
			if (other.processName != null)
				return false;
		} else if (!processName.equals(other.processName))
			return false;
		return true;
	}

	/**
	 * Gets the initial pos.
	 *
	 * @return the initial pos
	 */
	public int getInitialPos() { return initialPos; }

	/**
	 * Sets the initial pos. Also change process to execution.
	 *
	 * @param initialPos the new initial pos
	 */
	public void setInitialPos(int initialPos) {
		this.initialPos = initialPos;
		this.changeToExecution();
	}
}
