package model.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

import model.Processor;
import model.Process;
import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.MemoryPracticeIOException;

public class ProcessorLoaderFile implements IProcessorLoader {
	private BufferedReader br;
	
	public ProcessorLoaderFile(BufferedReader reader) {
		Objects.requireNonNull(reader);
		this.br = reader;
	}
	
	// process name arrivalTime executionTime neededMemory

	@Override
	public void loadProcesses(Processor p) throws MemoryPracticeIOException, InvalidProcessNeededMemory {
		try {
			String line;
			while((line = this.br.readLine()) != null) {
				String[] parts = line.split("\\s+");
				int paramNumbers[] = null;
				
				// Command -> exit:
				if(parts[0].equals("exit")) return;
				
				// Command -> process:
				if(parts[0].equals("process")) {
					// Check length:
					if(parts.length != 5) throw new MemoryPracticeIOException("Error in putCrafts from " + this.getClass().getName() + ": Invalid command");
					
					try {
						paramNumbers = new int[] {Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])};
					} catch(NumberFormatException e) {
						throw new MemoryPracticeIOException(e.getMessage());
					}
				}
				
				// Command -> endput:
				if(parts[0].equals("endput")) return;
				
				// Command -> invalid command:
				if(parts[0].equals("exit") == false && parts[0].equals("process") == false && parts[0].equals("endput") == false) 
					throw new MemoryPracticeIOException("Error in putCrafts from " + this.getClass().getName() + ": Invalid command");
				
				// If we haven't any exception...
				p.addProcessToQueue(new Process(parts[1], paramNumbers[0], paramNumbers[1], paramNumbers[2]));
				
			}
		} catch(IOException e) {
			throw new MemoryPracticeIOException("IO exception in loadProcesses from " + this.getClass().getName());
		}
		
		
	}

}
