package model.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import model.Processor;
import model.Process;
import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.MemoryPracticeIOException;
import model.exceptions.ProcessAddingException;

public class ProcessorLoaderFile implements IProcessorLoader {
	private BufferedReader br;
	
	public ProcessorLoaderFile(String route) throws MemoryPracticeIOException {
		Objects.requireNonNull(route);
		try {
			this.br = new BufferedReader(new FileReader(route));
		} catch (FileNotFoundException e) {
			throw new MemoryPracticeIOException(e.getMessage());
		}
	}
	
	// process name executionTime neededMemory

	/**
	 * Load processes.
	 *
	 * @param p the p
	 * @throws MemoryPracticeIOException the memory practice IO exception
	 * @throws InvalidProcessNeededMemory the invalid process needed memory
	 * @throws ProcessAddingException the process adding exception
	 */
	@Override
	public void loadProcesses(Processor p) throws MemoryPracticeIOException, InvalidProcessNeededMemory, ProcessAddingException {
		Objects.requireNonNull(p);
		try {
			String line;
			int arrivalTime = 0;
			while((line = this.br.readLine()) != null) {
				String[] parts = line.split("\\s+");
				int paramNumbers[] = null;
				
				// Command -> exit:
				if(parts[0].equals("exit")) return;
				
				// Command -> process:
				if(parts[0].equals("process")) {
					// Check length:
					if(parts.length != 4) throw new MemoryPracticeIOException("Error in putCrafts from " + this.getClass().getName() + ": Invalid command");
					
					try {
						paramNumbers = new int[] {Integer.parseInt(parts[2]), Integer.parseInt(parts[3])};
					} catch(NumberFormatException e) {
						throw new MemoryPracticeIOException(e.getMessage());
					}
				}
				
				// Command -> invalid command:
				if(parts[0].equals("exit") == false && parts[0].equals("process") == false) 
					throw new MemoryPracticeIOException("Error in putCrafts from " + this.getClass().getName() + ": Invalid command");
				
				// If we haven't any exception...
				p.addProcessToQueue(new Process(parts[1], arrivalTime, paramNumbers[0], paramNumbers[1]));
				arrivalTime++;
			} // WhileEND
		} catch(IOException e) {
			throw new MemoryPracticeIOException("IO exception in loadProcesses from " + this.getClass().getName());
		}
		
		
	}

}
