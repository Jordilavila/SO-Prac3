package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.ProcessAddingException;
import model.exceptions.ProcessExecutionTimeExceeded;

/**
 * The Class Processor.
 * @author Jordi Sellés Enríquez
 */
public abstract class Processor {
	
	/** The queue. */
	protected Set<Process> queue;
	
	/** The exec hash list. */
	protected int[] execHashList;
	
	/** The exec processes. */
	protected ArrayList<Process> execProcesses;
	
	/** The total memory. */
	protected int totalMemory;
	
	/** The Constant MINIMAL_PROCESSOR_MEMORY_SIZE. */
	protected final static int MINIMAL_PROCESSOR_MEMORY_SIZE = 1024;
	
	/** The Constant MINIMAL_PROCESS_MEMORY_SIZE. */
	protected final static int MINIMAL_PROCESS_MEMORY_SIZE = 100;
	
	/** The Constant FREE_MEMORY_SPACE_IDENDIFYER. */
	protected final static int FREE_MEMORY_SPACE_IDENDIFYER = -1;
	
	protected Set<Process> killedProcesses;
	
	/**
	 * Instantiates a new processor.
	 *
	 * @param totalMemory the total memory
	 */
	public Processor(int totalMemory) {
		if(totalMemory <= Processor.MINIMAL_PROCESSOR_MEMORY_SIZE) throw new NumberFormatException("totalMemory have to be greater than " + Processor.MINIMAL_PROCESSOR_MEMORY_SIZE);
		
		this.totalMemory = totalMemory;
		this.execHashList = new int[totalMemory];
		this.queue = new LinkedHashSet<Process>();
		this.execProcesses = new ArrayList<Process>();
		this.killedProcesses = new LinkedHashSet<>();
		for(int i=0; i<this.totalMemory; i++) this.execHashList[i] = Processor.FREE_MEMORY_SPACE_IDENDIFYER;
	}
	
	/**
	 * Move process from queue to exec.
	 *
	 * @param p the p
	 * @return true, if successful
	 * @throws ProcessAddingException the process adding exception
	 */
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
	
	/**
	 * Move process from execution to queue.
	 *
	 * @param p the p
	 * @return true, if successful
	 * @throws ProcessAddingException the process adding exception
	 * @throws InvalidProcessNeededMemory the invalid process needed memory
	 */
	public void moveProcessFromExecToQueue(Process p) throws ProcessAddingException, InvalidProcessNeededMemory {
		if(this.execProcesses.contains(p)) {
			int processHash = p.hashCode();
			this.quitProcessFromExecution(processHash);
			this.addProcessToQueue(p);
			p.quitFromExecution();
			return;
		}
		throw new ProcessAddingException(p, "The process is not running");
	}
	
	public void moveProcessFromExecToKilled(Process p) throws InvalidProcessNeededMemory, ProcessAddingException {
		if(this.isInExecution(p)) {
			this.quitProcessFromExecution(p.hashCode());
			this.addProcessToKilledProcesses(p);
			p.quitFromExecution();
			return;
		}
		throw new ProcessAddingException(p, "The process is not running");
	}
	
	/**
	 * Adds the process to killed processes.
	 *
	 * @param p the p
	 * @return true, if successful
	 * @throws InvalidProcessNeededMemory the invalid process needed memory
	 * @throws ProcessAddingException the process adding exception
	 */
	public boolean addProcessToKilledProcesses(Process p) throws InvalidProcessNeededMemory, ProcessAddingException {
		Objects.requireNonNull(p);
		if(p.getNeededMemory() < Processor.MINIMAL_PROCESS_MEMORY_SIZE || p.getNeededMemory() > this.getTotalMemory()) throw new InvalidProcessNeededMemory(p);
		if(this.killedProcesses.add(p)) {
			return true;
		}
		throw new ProcessAddingException(p, "Process already exists in killedProcesses");
	}
	
	/**
	 * Adds the process to queue.
	 *
	 * @param p the process
	 * @return true, if successful
	 * @throws InvalidProcessNeededMemory 
	 * @throws ProcessAddingException 
	 */
	public boolean addProcessToQueue(Process p) throws InvalidProcessNeededMemory, ProcessAddingException {
		if(p.getNeededMemory() < Processor.MINIMAL_PROCESS_MEMORY_SIZE || p.getNeededMemory() > this.getTotalMemory()) throw new InvalidProcessNeededMemory(p);
		if(this.queue.add(p)) {
			return true;
		}
		throw new ProcessAddingException(p, "Process already exists in queue");
	}
	
	/**
	 * Look for empty spaces.
	 *
	 * @return a map with count and size of empty spaces. 
	 * If there aren't anyone
	 */
	protected Map<Integer, Integer> lookForEmptySpaces(){
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
		boolean protectInit = false;
		int emptySpaceSize = 0;
		int lastKey = 0;
		// Check if memory is clean and returns a null hashmap if successful:
		if(this.isMemoryClean()) { return ret; }
		
		for(int i=0; i<this.totalMemory; i++) {
			if(this.checkFreeMemoryPosition(this.execHashList[i])) {
				emptySpaceSize++;
				if(!protectInit) { // Creamos una nueva entrada
					ret.put(i, emptySpaceSize);
					protectInit = true;
					lastKey = i;
				} else {
					ret.replace(lastKey, emptySpaceSize);
				}
			}
			
			if(i>0) {
				// Si la posición actual está ocupada y la anterior está libre...
				if(!this.checkFreeMemoryPosition(this.execHashList[i]) && this.checkFreeMemoryPosition(this.execHashList[i - 1])) {
					protectInit = false;
					emptySpaceSize = 0;
				}
			}
			
		}
		return ret;
	}
	
	/**
	 * Check where process can be added.
	 *
	 * @param p the process
	 * @return the position where we can add it, if successful. Otherwise, -1.
	 */
	protected abstract int checkWhereProcessCanBeAdded(Process p);
	
	/**
	 * Clean all memory.
	 */
	protected void cleanAllMemory() { for(int i=0; i<this.totalMemory; i++) this.execHashList[i] = Processor.FREE_MEMORY_SPACE_IDENDIFYER; }
	
	/**
	 * Checks if is memory clean.
	 *
	 * @return true, if is memory clean
	 */
	public boolean isMemoryClean() { return execProcesses.isEmpty(); }
	
	/**
	 * Quit process from int array of execution processes.
	 *
	 * @param processHash the process hash
	 */
	protected void quitProcessFromExecution(int processHash) {
		boolean exists = false;
		for(Process it : this.getCopyOfExecProcesses()) {
			if(it.hashCode() == processHash) {
				exists = true;
				this.execProcesses.remove(it);
			}
		}
		for(int i=0; i<this.execHashList.length && exists; i++) {
			if(this.execHashList[i] == processHash) {
				this.execHashList[i] = Processor.FREE_MEMORY_SPACE_IDENDIFYER;
			}
		}
	}
	
	/**
	 * Checks if is in execution.
	 *
	 * @param p the process
	 * @return true, if is in execution
	 */
	public boolean isInExecution(Process p) {
		Objects.requireNonNull(p);
		for(Process it : this.execProcesses) {
			if(it.equals(p)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if is queued.
	 *
	 * @param p the process
	 * @return true, if is queued
	 */
	public boolean isQueued(Process p) {
		Objects.requireNonNull(p);
		return this.queue.contains(p);
	}
	
	/**
	 * Ask if process is terminated.
	 *
	 * @param p the process
	 * @return true, if successful
	 * @throws ProcessExecutionTimeExceeded the process execution time exceeded
	 */
	public boolean askIfProcessIsTerminated(Process p) throws ProcessExecutionTimeExceeded {
		Objects.requireNonNull(p);
		if(p.getInternalCounter() < p.getExecutionTime()) {
			return false;
		} else if(p.getInternalCounter() == p.getExecutionTime()) {
			return true;
		} else {
			throw new ProcessExecutionTimeExceeded(p);
		}
	}
	
	/**
	 * Increment processes counter.
	 */
	public void incrementProcessesCounter() {
		for(Process it : this.getExecProcesses())
			it.incrementInternalCounter();
	}
	
	/**
	 * Check free memory position.
	 *
	 * @param position the position
	 * @return true, if successful
	 */
	protected boolean checkFreeMemoryPosition(int position) {
		return ((position == Processor.FREE_MEMORY_SPACE_IDENDIFYER) ? true : false);
	}
	
	/**
	 * Gets the total memory.
	 *
	 * @return the total memory
	 */
	public int getTotalMemory() { return this.totalMemory; }
	
	/**
	 * Gets a DEFENSIVE COPY of the exec processes.
	 *
	 * @return a DEFENSIVE COPY of exec processes
	 */
	public Set<Process> getCopyOfExecProcesses() {
		Set<Process> ret = new HashSet<Process>();
		for(Process it : this.execProcesses) {
			ret.add(it.copy());
		}
		return ret;
	}
	
	/**
	 * Gets the exec processes.
	 *
	 * @return the exec processes
	 */
	public ArrayList<Process> getExecProcesses() { return this.execProcesses; }
	
	/**
	 * Gets the ordered processes list.
	 *
	 * @return the ordered processes list as ArrayList
	 */
	private ArrayList<Process> getOrderedProcessesList(){
		ArrayList<Process> processes = this.execProcesses;
		ArrayList<Process> orderedProcesses = new ArrayList<Process>();
		int[] sizesList = new int[processes.size()];
		
		int i = 0;
		for(Process it : processes) {
			sizesList[i] = it.getInitialPos();
			i++;
		}
		
		for(int r = 1; r < sizesList.length; r++) {
			for(int s = 0; s < (sizesList.length - r); s++) {
				if(sizesList[s] > sizesList[s + 1]) {
					int aux = sizesList[s];
					sizesList[s] = sizesList[s + 1];
					sizesList[s + 1] = aux;
				}
			}
		}
		
		for(int itInt : sizesList) {
			for(Process itPro : processes) {
				if(itInt == itPro.getInitialPos()) orderedProcesses.add(itPro);
			}
		}
		return orderedProcesses;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		String ret = "=== IN EXECUTION ===\n";
		ArrayList<Process> orderedProcesses = this.getOrderedProcessesList();
		
		for(Process it : orderedProcesses) {
			ret += it.toString();
			ret += "\n";
		}
		
		ret += "=== QUEUE ===\n";
		for(Process it : this.queue) {
			ret += it.toString();
			ret += "\n";
		}
		
		ret += "=== FINALIZED ===\n";
		for(Process it : this.killedProcesses) {
			ret += it.toString();
			ret += "\n";
		}
		return ret;
	}
	
	/**
	 * Gets the queue as a TreeSet.
	 *
	 * @return the queue
	 */
	public Set<Process> getQueue(){
		return this.queue;
	}
	
	/**
	 * Gets the copy of queue as a TreeSet.
	 *
	 * @return the copy of queue
	 */
	public Set<Process> getCopyOfQueue() {
		Set<Process> ret = new LinkedHashSet<Process>();
		for(Process it : this.getQueue()) {
			ret.add(it.copy());
		}
		return ret;
	}
	
	public ArrayList<Process> getQueueAsArrayList() {
		ArrayList<Process> ret = new ArrayList<Process>();
		for(Process it : this.getQueue()) {
			ret.add(it);
		}
		return ret;
	}
	
	/**
	 * Gets a copy of the finalized processes.
	 *
	 * @return the finalized processes
	 */
	public Set<Process> getCopyOfFinalizedProcesses() {
		Set<Process> ret = new HashSet<Process>();
		for(Process it : this.getExecProcesses()) {
			try {
				if(it.isFinalized()) {
					ret.add(it.copy());
				}
			} catch (ProcessExecutionTimeExceeded e) {
				// Aquí se habría pasado el tiempo de ejecución, por lo que nos lo cargamos
				ret.add(it.copy());
			}
		}
		return ret;
	}
	
	public Set<Process> getFinalizedProcesses() {
		Set<Process> ret = new HashSet<Process> ();
		for(Process it : this.getExecProcesses()) {
			try {
				if(it.isFinalized()) {
					ret.add(it);
				}
			} catch (ProcessExecutionTimeExceeded e) {
				// Here the execution time is passed, so kill it
				ret.add(it);
			}
		}
		return ret;
	}
}




























