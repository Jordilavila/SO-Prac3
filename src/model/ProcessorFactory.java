package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import model.exceptions.InvalidProcessorTypeException;

/**
 * A factory for creating Processor objects using reflection.
 * 
 * @author Jordi Sellés Enríquez
 */
public abstract class ProcessorFactory {
	
	/**
	 * Creates a new Processor object.
	 *
	 * @param type the type (ProcessorBest or ProcessorWorst)
	 * @param totalMemory the total memory
	 * @return the processor
	 * @throws InvalidProcessorTypeException the invalid processor type exception
	 */
	public static Processor createProcessor(String type, int totalMemory) throws InvalidProcessorTypeException {
		
		try {
			Class<? extends Processor> clazz = (Class<? extends Processor>) Class.forName("model." + type);
			Constructor<? extends Processor> co = clazz.getDeclaredConstructor(int.class);
			return co.newInstance(totalMemory);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new InvalidProcessorTypeException(e.getMessage());
		}
		
	}
}
