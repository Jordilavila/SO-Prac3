package model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.*;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.ProcessAddingException;

public class testProcessor {
	Processor ryzen5, i5;
	Process A, B, C;
	
	@Before
	public void setUp() {
		A = new Process("A", 0, 200, 500);
		B = new Process("B", 0, 500, 1000);
		C = new Process("C", 1, 300, 1500);
		ryzen5 = new ProcessorBest(2000);
		i5 = new ProcessorWorst(2000);
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
			fail("Debáa producirse una excepción");
		} catch(ProcessAddingException e) {
			// Bien
		} catch (InvalidProcessNeededMemory e) {
			fail("Excepción incorrecta");
		}
		
		try {
			ryzen5.addProcessToQueue(A.copy());
			fail("Debía producirse una excepción");
		} catch(ProcessAddingException e) {
			// Bien
		} catch (InvalidProcessNeededMemory e) {
			fail("Excepción incorrecta");
		}
	}
	
	@Test
	public void testQuitProcessFromExecution() {
		// Adding process to queue:
		try {
			assertTrue(ryzen5.addProcessToQueue(A));
		} catch(InvalidProcessNeededMemory | ProcessAddingException e) {
			fail("El proceso tenía que añadirse");
		}
		
		// Moving process to exec:
		try {
			assertTrue(ryzen5.moveProcessFromQueueToExec(A));
		} catch (ProcessAddingException e) {
			fail("Tendría que haberse añadido: " + e.toString());
		}
		Set<Process> execProcesses = ryzen5.getExecProcesses();
		
		// Returning process to Queue:
		try {
			ryzen5.moveProcessFromExecToQueue(A);
		} catch (ProcessAddingException | InvalidProcessNeededMemory e) {
			fail("Tendría que haberse quitado");
		}
		assertNotEquals("", execProcesses, ryzen5.getExecProcesses());
	}
	
	@Test
	public void testCheckIfProcessCanBeAddedBest() throws InvalidProcessNeededMemory, ProcessAddingException {
		Process r = new Process("r", 0, 1, 500);
		Process s = new Process("s", 0, 1, 500);
		Process t = new Process("t", 0, 1, 500);
		Process u = new Process("u", 0, 1, 400);
		Process w = new Process("w", 0, 1, 100);
		ryzen5.addProcessToQueue(r);
		ryzen5.addProcessToQueue(s);
		ryzen5.addProcessToQueue(t);
		ryzen5.addProcessToQueue(u);
		ryzen5.addProcessToQueue(w);
		
		assertTrue(ryzen5.moveProcessFromQueueToExec(r));
		assertTrue(ryzen5.moveProcessFromQueueToExec(s));
		assertTrue(ryzen5.moveProcessFromQueueToExec(t));
		assertTrue(ryzen5.moveProcessFromQueueToExec(u));
		
		ryzen5.moveProcessFromExecToQueue(s);
		
		ryzen5.moveProcessFromQueueToExec(w);
		
		// [ InitPos NAME FinalPos ]
		//System.out.println(ryzen5.execProcesses.toString());
		
		assertEquals(0, r.getInitialPos());
		assertEquals(1000, t.getInitialPos());
		assertEquals(1500, u.getInitialPos());
		assertEquals(1900, w.getInitialPos());
	}
	
	@Test
	public void testCheckIfProcessCanBeAddedWorst() throws InvalidProcessNeededMemory, ProcessAddingException {
		Process r = new Process("r", 0, 1, 500);
		Process s = new Process("s", 0, 1, 500);
		Process t = new Process("t", 0, 1, 500);
		Process u = new Process("u", 0, 1, 400);
		Process w = new Process("w", 0, 1, 100);
		i5.addProcessToQueue(r);
		i5.addProcessToQueue(s);
		i5.addProcessToQueue(t);
		i5.addProcessToQueue(u);
		i5.addProcessToQueue(w);
		
		assertTrue(i5.moveProcessFromQueueToExec(r));
		assertTrue(i5.moveProcessFromQueueToExec(s));
		assertTrue(i5.moveProcessFromQueueToExec(t));
		assertTrue(i5.moveProcessFromQueueToExec(u));
		
		i5.moveProcessFromExecToQueue(s);
		
		i5.moveProcessFromQueueToExec(w);
		
		// [ InitPos NAME FinalPos ]
		System.out.println(i5.execProcesses.toString());
		
		assertEquals(0, r.getInitialPos());
		assertEquals(1000, t.getInitialPos());
		assertEquals(1500, u.getInitialPos());
		assertEquals(500, w.getInitialPos());
	}
	
	@Test
	public void testProcessorToString() throws InvalidProcessNeededMemory, ProcessAddingException {
		Process r = new Process("r", 0, 1, 500);
		Process s = new Process("s", 0, 1, 500);
		Process t = new Process("t", 0, 1, 500);
		Process u = new Process("u", 0, 1, 400);
		Process w = new Process("w", 0, 1, 100);
		ryzen5.addProcessToQueue(r);
		ryzen5.addProcessToQueue(s);
		ryzen5.addProcessToQueue(t);
		ryzen5.addProcessToQueue(u);
		ryzen5.addProcessToQueue(w);
		assertTrue(ryzen5.moveProcessFromQueueToExec(r));
		assertTrue(ryzen5.moveProcessFromQueueToExec(s));
		assertTrue(ryzen5.moveProcessFromQueueToExec(t));
		assertTrue(ryzen5.moveProcessFromQueueToExec(u));
		ryzen5.moveProcessFromExecToQueue(s);
		ryzen5.moveProcessFromQueueToExec(w);
		System.out.println(ryzen5.toString());
	}
}
