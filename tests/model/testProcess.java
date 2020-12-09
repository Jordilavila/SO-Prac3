package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class testProcess {
	String process1name, process2name;
	int process1executionTime, process2executionTime;
	int process1arrivalTime, process2arrivalTime;
	int process1neededMemory, process2neededMemory;
	
	String process1expectedInfo = 
		      "==================================================\n"
			+ "Process name: A\n"
			+ "==================================================\n"
			+ "Arrival time: 0\n"
			+ "Execution time: 20\n"
			+ "Internal counter: 0\n"
			+ "Needed memory: 200\n"
			+ "Status: Waiting\n"
			+ "__________________________________________________";
	
	String process1expectedToString = "[0 A 199]";
	
	
	@Before
	public void setUp() {
		process1name = "A";
		process2name = "B";
		process1executionTime = 20;
		process2executionTime = 15;
		process1arrivalTime = 0;
		process2arrivalTime = 2;
		process1neededMemory = 200;
		process2neededMemory = 300;
	}

	@Test
	public void testConstructor() {
		Process A = new Process(process1name, process1arrivalTime, process1executionTime, process1neededMemory);
		assertEquals(process1name, A.getProcessName());
		assertEquals(process1arrivalTime, A.getArrivalTime());
		assertEquals(process1executionTime, A.getExecutionTime());
	}
	
	/*
	 * With this test I'm checking the getters
	 */
	@Test
	public void testInfo() {
		Process A = new Process(process1name, process1arrivalTime, process1executionTime, process1neededMemory);
		assertEquals(process1expectedInfo, A.info());
	}
	
	@Test
	public void testToString() {
		Process A = new Process(process1name, process1arrivalTime, process1executionTime, process1neededMemory);
		A.setInitialPos(0);
		assertEquals(process1expectedToString, A.toString());
	}
}
