package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.InvalidProcessorTypeException;

public class testProcessorFactory {

	Processor ryzen5, i5;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testProcessorFactoryCreate() {
		try {
			ryzen5 = ProcessorFactory.createProcessor("ProcessorBest", 2000);
		} catch (InvalidProcessorTypeException e) {
			fail("No debería de lanzar excepciones: " + e.getMessage());
		}
		assertEquals(ProcessorBest.class, ryzen5.getClass());
		assertEquals(2000, ryzen5.getTotalMemory());
		
		try {
			i5 = ProcessorFactory.createProcessor("ProcessorWorst", 2000);
		} catch (InvalidProcessorTypeException e) {
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
			fail("Debería de haber lanzado una InvalidProcessorTypeException");
		} catch (InvalidProcessorTypeException e) {
			// Bien
		}
	}
}
