package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

import model.exceptions.InvalidProcessNeededMemory;
import model.exceptions.InvalidProcessorTypeException;
import model.exceptions.MemoryPracticeIOException;
import model.exceptions.MemoryPracticeRuntimeException;
import model.exceptions.ProcessAddingException;
import model.exceptions.UnexistentProcessException;
import model.io.IViewer;
import model.io.ViewerConsole;

// process name executionTime neededMemory

public class testMemoryManager {
	final static String DIRFILES = "tests/files/";
	MemoryPractice mP;
	final static int totalMemory = 2000;

	@Test
	public void testRunMemoryPractice1() throws InvalidProcessorTypeException, MemoryPracticeIOException, MemoryPracticeRuntimeException {
		final String outFile = DIRFILES + "runMemoryPractice1.data";
		mP = new MemoryPractice("BEST", totalMemory, DIRFILES + "runMemoryPractice1.in");
		IViewer iv = new ViewerConsole(mP);
		PrintStream ps = standardIO2File(outFile);
		if(ps != null) {
			mP.run(iv);
			assertTrue(mP.isFinalized());
			System.setOut(System.out); // Se reestablece la salida estándar
			ps.close();
		} else {
			fail("Error: No se pudo crear el fichero " + outFile);
		}
	}
	
	@Test
	public void testRunMemoryPractice1_2() throws InvalidProcessorTypeException, UnexistentProcessException, MemoryPracticeIOException, InvalidProcessNeededMemory, ProcessAddingException, MemoryPracticeRuntimeException {
		mP = new MemoryPractice("BEST", totalMemory, DIRFILES + "runMemoryPractice1.in");
		IViewer iv = new ViewerConsole(mP);
		mP.run(iv);
	}

	/*
	 * Este test trabaja con el algoritmo de mejor hueco y 5 procesos. Funciona perfectamente.
	 */
	@Test
	public void testRunMemoryPractice2BEST() throws InvalidProcessorTypeException, MemoryPracticeIOException, MemoryPracticeRuntimeException {
		final String outFile = DIRFILES + "runMemoryPractice2BEST.data";
		mP = new MemoryPractice("BEST", 2000, DIRFILES + "runMemoryPractice2.in");
		IViewer iv = new ViewerConsole(mP);
		PrintStream ps = standardIO2File(outFile);
		if(ps != null) {
			mP.run(iv);
			assertTrue(mP.isFinalized());
			System.setOut(System.out);
			ps.close();
		} else {
			fail("Error: No se pudo crear el fichero " + outFile);
		}
		
		//Se compara salida con la solución
		StringBuilder sbSolution = readFromFile(DIRFILES + "runMemoryPractice2BEST.sol");
		StringBuilder sbObtenido = readFromFile(DIRFILES + "runMemoryPractice2BEST.data");
		compareLines(sbSolution.toString(), sbObtenido.toString());
	}
	@Test
	public void testRunMemoryPractice2BEST_CONSOLE() throws InvalidProcessorTypeException, UnexistentProcessException, MemoryPracticeIOException, InvalidProcessNeededMemory, ProcessAddingException, MemoryPracticeRuntimeException {
		mP = new MemoryPractice("BEST", totalMemory, DIRFILES + "runMemoryPractice2.in");
		IViewer iv = new ViewerConsole(mP);
		mP.run(iv);
	}
	
	@Test
	public void testRunMemoryPractice2WORST() throws InvalidProcessorTypeException, MemoryPracticeIOException, MemoryPracticeRuntimeException {
		final String outFile = DIRFILES + "runMemoryPractice2WORST.data";
		mP = new MemoryPractice("WORST", 2000, DIRFILES + "runMemoryPractice2.in");
		IViewer iv = new ViewerConsole(mP);
		PrintStream ps = standardIO2File(outFile);
		if(ps != null) {
			mP.run(iv);
			assertTrue(mP.isFinalized());
			System.setOut(System.out);
			ps.close();
		} else {
			fail("Error: No se pudo crear el fichero " + outFile);
		}
	}
	
	@Test
	public void testRunMemoryPractice2WORST_CONSOLE() throws InvalidProcessorTypeException, MemoryPracticeIOException, MemoryPracticeRuntimeException {
		mP = new MemoryPractice("WORST", totalMemory, DIRFILES + "runMemoryPractice2.in");
		IViewer iv = new ViewerConsole(mP);
		mP.run(iv);
	}
	
	
	// FUNCIONES AUXILIARES
	//Redirección de la salida estandard a un fichero	
	public static PrintStream standardIO2File(String fileName){

		if(fileName.equals("")){//Si viene vacío usamos este por defecto
			fileName="C:\\javalog.txt";
		}
		PrintStream ps=null;
		try {
			//Creamos un printstream sobre el archivo.
			ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(fileName))),true);
			//Redirigimos salida estandar
			System.setOut(ps);
			// System.setErr(ps);
		} catch (FileNotFoundException ex) {
			System.err.println("Se ha producido una excepción FileNotFoundException");
		}
		return ps;
	}

	//Lee la solución de un fichero y la devuelve en un StringBuilder	
	private StringBuilder readFromFile(String file) {
		Scanner sc=null;
		try {
			sc = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		while (sc.hasNext()) 
			sb.append(sc.nextLine()+"\n");			
		sc.close();
		return (sb);
	}

	//Compara dos Strings línea a línea
	private void  compareLines(String expected, String result) {
		String exp[]=expected.split("\n");
		String res[]=result.split("\n");
		boolean iguales = true;
		if (exp.length!=res.length) 
			fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
		for (int i=0; i<exp.length && iguales; i++) {
			if (! exp[i].contains("Action by")) {
				assertEquals("linea "+i, exp[i].trim(),res[i].trim());
			}
		}
	}

	String getFileExtension(String name) {
		int extIndex = name.lastIndexOf(".");

		if (extIndex == -1) {
			return "";
		} else {
			return name.substring(extIndex + 1);
		}
	}

	//Compara dos ficheros gifs
	boolean compareFiles(String f1, String f2) throws IOException {
		String comando = null;
		if (! getFileExtension(f1).equals("gif"))
			throw new IOException("Error: No es un fichero gif");

		if (!new File(f1).exists())  {
			System.out.println("El fichero "+f1+ "no existe.");
			return false;
		}
		if (!new File(f2).exists())  {
			System.out.println("El fichero "+f2+ "no existe.");
			return false;
		}
		try {
			comando = new String("cmp -b "+f1+" "+f2);
			// Ejecutamos el comando definido
			java.lang.Process p = Runtime.getRuntime().exec(comando);

			// Instanciamos un lector del buffer para mostrar resultado
			BufferedReader resultado = new BufferedReader(new InputStreamReader(p.getInputStream()));
			// System.out.println("Resultado del comando:");
			String diferencias = resultado.readLine();
			if (diferencias!=null && diferencias.length()!=0) {
				while (diferencias!= null){
					System.out.println(diferencias);
					diferencias=resultado.readLine();
				}
				return false;
			} 
			else return true;
		} catch (IOException ex) {
			return false;
		}
	}
}
