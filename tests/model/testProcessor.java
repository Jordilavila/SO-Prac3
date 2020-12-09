package model;

import static org.junit.Assert.*;

import org.junit.*;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.ProcessAddingException;

public class testProcessor {
	Processor ryzen5;
	Process A, B, C;
	
	@Before
	public void setUp() {
		A = new Process("A", 0, 200, 500);
		B = new Process("B", 0, 500, 500);
		C = new Process("C", 1, 300, 1500);
		ryzen5 = new ProcessorBest(2000);
	}
	
	@Test
	public void testProcessorAddToQueue() {
		try {
			assertTrue(ryzen5.addProcessToQueue(A));
			assertTrue(ryzen5.addProcessToQueue(B));
			assertTrue(ryzen5.addProcessToQueue(C));
		} catch (InvalidProcessNeededMemory | ProcessAddingException e) {
			fail("No se ha podido añadir: " + e.getMessage());
		}
		//System.out.println("\n" + ryzen5.queue.toString() + "\n");
		
		try {
			ryzen5.addProcessToQueue(B);
			fail("Debió producirse una excepción");
		} catch(ProcessAddingException e) {
			// Bien
		} catch (InvalidProcessNeededMemory e) {
			fail("Excepción incorrecta");
		}
	}
}
