package model;

import static org.junit.Assert.fail;

import org.junit.*;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.ProcessAddingException;

public class testProcessor {
	Processor ryzen5;
	Process A;
	
	@Before
	public void setUp() {
		A = new Process("A", 0, 200, 512);
		ryzen5 = new ProcessorBest(2000);
	}
	
	@Test
	public void testProcessorAddToQueue() {
		try {
			ryzen5.addProcessToQueue(A);
		} catch (InvalidProcessNeededMemory | ProcessAddingException e) {
			fail("No se ha podido añadir: " + e.getMessage());
		}
	}
}
