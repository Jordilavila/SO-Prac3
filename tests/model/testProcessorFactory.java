package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;

public class testProcessorFactory {

	Processor ryzen5, i5;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testProcessorFactoryCreate() {
		try {
			ryzen5 = ProcessorFactory.createProcessor("ProcessorBest", 2000);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("No debería de lanzar excepciones: " + e.getMessage());
		}
		assertEquals(ProcessorBest.class, ryzen5.getClass());
		assertEquals(2000, ryzen5.getTotalMemory());
		
		try {
			i5 = ProcessorFactory.createProcessor("ProcessorWorst", 2000);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("No debería de lanzar excepciones: " + e.getMessage());
		}
		assertEquals(ProcessorBest.class, ryzen5.getClass());
		assertEquals(2000, ryzen5.getTotalMemory());
	}
	
	@Test
	public void testProcessorFactoryExceptions() {
		@SuppressWarnings("unused")
		Processor unexistent;
		try {
			unexistent = ProcessorFactory.createProcessor("Pepe", 2000);
			fail("Debería de haber lanzado una ClassNotFoundException");
		} catch (ClassNotFoundException e) {
			// Bien
			
		} catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			fail("Debería de haber lanzado una ClassNotFoundException");
		}
	}
}
