package model.io;

import java.util.Objects;

import model.MemoryPractice;

/**
 * The Class ViewerConsole.
 * @author Jordi Sellés Enríquez
 */
public class ViewerConsole implements IViewer {
	
	/** The mp. */
	private MemoryPractice mp;
	
	/**
	 * Instantiates a new viewer console.
	 *
	 * @param mp the mp
	 */
	public ViewerConsole(MemoryPractice mp) {
		Objects.requireNonNull(mp);
		this.mp = mp;
	}

	/**
	 * Show.
	 */
	@Override
	public void show() {
		System.out.println(this.mp.toString());
	}

	/**
	 * Close.
	 * 
	 * This method don't do anything
	 */
	@Override
	public void close() {
		// This method don't do anything
	}

}
