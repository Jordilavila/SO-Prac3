package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.MemoryPracticeIOException;
import model.io.IProcessorLoader;
import model.io.ProcessorLoaderFile;

public class testProcessorLoaderFile {
	final static String DIRFILES = "tests/files/";
	Processor ryzen5;
	static String emptyProcessor, emptyProcessor2, processorOk1;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		emptyProcessor =
				"[]";
		
		emptyProcessor2 =
				  "=== IN EXECUTION ===\n"
				+ "=== QUEUE ===\n";
		
		processorOk1 =
				  "=== IN EXECUTION ===\n"
				+ "=== QUEUE ===\n"
				+ "[-1 TEST_PROCESS 249]\n"
				+ "[-1 TEST_PROCESS_2 499]\n";
	}

	@Before
	public void setUp() {
		ryzen5 = new ProcessorBest(2000);
	}
	
	@Test
	public void testProcessorLoaderNullPointerException() throws MemoryPracticeIOException {
		try {
			new ProcessorLoaderFile(null);
			fail("Debería lanzar una NPE");
		} catch (NullPointerException e) {
			// Bien
		}
	}
	
	@Test
	public void testLoadProcessesEmptyFile() throws MemoryPracticeIOException {
		IProcessorLoader ip = new ProcessorLoaderFile(DIRFILES + "empty.in");
		ip.loadProcesses(ryzen5);
		compareLines(emptyProcessor, ryzen5.getExecProcesses().toString());
		compareLines(emptyProcessor, ryzen5.queue.toString());
	}
	
	@Test
	public void testLoadProcessOk1() throws MemoryPracticeIOException {
		IProcessorLoader ip = new ProcessorLoaderFile(DIRFILES + "loadProcessOk1.in");
		ip.loadProcesses(ryzen5);
		compareLines(processorOk1, ryzen5.toString());
	}
	
	@Test
	public void testLoadProcessWithFails() throws MemoryPracticeIOException {
		IProcessorLoader ip = new ProcessorLoaderFile(DIRFILES + "loadProcessFail.in");
		try {
			ip.loadProcesses(ryzen5);
			fail("Debía lanzar una excepción");
		} catch (MemoryPracticeIOException e) {
			compareLines(emptyProcessor2, ryzen5.toString());
		}
	}
	
	
	// FUNCIONES AUXILIARES
	private void compareLines(String expected, String result) {
		String exp[]=expected.split("\n");
		String res[]=result.split("\n");
		if (exp.length!=res.length) 
			fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
		for (int i=0; i<exp.length; i++) {
			 				 assertEquals("linea "+i, exp[i],res[i]);
		}
	}
}
