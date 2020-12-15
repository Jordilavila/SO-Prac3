package model.io;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import model.MemoryPractice;
import model.Process;
import model.exceptions.MemoryPracticeIOException;
import model.io.gif.AnimatedGIF;
import model.io.gif.FrameGIF;

/**
 * The Class ViewerGIF.
 * @author Jordi Sellés Enríquez
 */
public class ViewerGIF implements IViewer {
	
	/** The mp. */
	private MemoryPractice mp;
	
	/** The agif. */
	private AnimatedGIF agif;
	
	/** The row max. */
	public final static int rowMax = 10;
	
	/** The col max. */
	public int colMax;
	
	private final static char emptySymbol = '*';
	
	/**
	 * Instantiates a new viewer GIF.
	 *
	 * @param mp the MemoryPractice
	 */
	public ViewerGIF(MemoryPractice mp) {
		Objects.requireNonNull(mp);
		this.mp = mp;
		this.agif = new AnimatedGIF();
		this.colMax = this.mp.getProcessor().getTotalMemory() / ViewerGIF.rowMax;
	}

	@Override
	public void show() {
		FrameGIF frame = new FrameGIF(colMax, rowMax);
		ArrayList<Process> exec = this.mp.getProcessor().getExecProcesses();
		
		for(int i=0; i<rowMax-1; i++)
			this.printLine(frame, i, this.colMax, Color.LIGHT_GRAY);
		
		for(Process it : exec) {
			for(int i=it.getInitialPos(); i<((it.getNeededMemory() + it.getInitialPos()) / ViewerGIF.rowMax); i++) {
				this.printLine(frame, i, this.colMax, Color.LIGHT_GRAY);
				//frame.printSquare(j, h, color);
				String name = it.getProcessName();
				switch (name) {
				case "A":
				case "F":
				case "K":
				case "P":
				case "U":
				case "Z":
					this.printLine(frame, i, this.colMax, Color.BLUE);
					break;
					
				case "B":
				case "G":
				case "L":
				case "Q":
				case "V":
					this.printLine(frame, i, this.colMax, Color.GREEN);
					break;
					
				case "C":
				case "H":
				case "M":
				case "R":
				case "W":
					this.printLine(frame, i, this.colMax, Color.MAGENTA);
					break;
					
				case "D":
				case "I":
				case "N":
				case "S":
				case "X":
					this.printLine(frame, i, this.colMax, Color.ORANGE);
					break;
					
				case "E":
				case "J":
				case "O":
				case "T":
				case "Y":
					this.printLine(frame, i, this.colMax, Color.PINK);
					break;
					
				default:
					this.printLine(frame, i, this.colMax, Color.LIGHT_GRAY);
					break;
				} // SWITCH END
			} // FOR END
		} // FOR END
		
		try {
			this.agif.addFrame(frame);
		} catch (MemoryPracticeIOException e) {
			throw new RuntimeException("ViewerGIF.show() Exception: " + e.toString());
		}
		
	}
	
	private void printLine(FrameGIF frame, int xMax, int h, Color color) {
		for(int j=0; j<xMax-1; j++) {
			try {
				frame.printSquare(h, j, color);
			} catch (MemoryPracticeIOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private String createBoard() {
		ArrayList<Process> exec = this.mp.getProcessor().getExecProcesses();
		String ret = "";
		
		for(int i=0; i<2000; i++) {
			ret += ViewerGIF.emptySymbol;
			if(i % 100 == 0)
				ret += "\n";
		}
		
		for(Process it : exec) {
			//ret.
		}
		
		return ret;
	}
	
	private String createGroup(Process p) {
		String ret = "";
		for(int i=0; i<p.getNeededMemory(); i++) {
			ret += p.getProcessName();
			if(i % 100 == 0)
				ret += "\n";
		}
		return ret;
	}

	@Override
	public void close() {
		try {
			agif.saveFile(new File("files/output.gif"));
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}

}
