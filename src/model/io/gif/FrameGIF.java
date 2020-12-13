package model.io.gif;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.exceptions.MemoryPracticeIOException;

/**
 * It generates a GIF frame using GIF4J
 * @author drizo
 *
 */
public class FrameGIF {

	/** The Constant PIXELS_SQUARE. */
	private static final int PIXELS_SQUARE = 50;
	
	/** The width. */
	private int width;
	
	/** The heigh. */
	private int heigh;

	/** The ig 2. */
	private Graphics2D ig2; // GIF4J
	
	/** The bi. */
	private BufferedImage bi; // GIF4J

	/**
	 * Instantiates a new frame GIF.
	 *
	 * @param w the w
	 * @param h the h
	 */
	public FrameGIF(int w, int h) {
		this.width = w;
		this.heigh = h;
		
		bi = new BufferedImage(w*PIXELS_SQUARE, h*PIXELS_SQUARE, BufferedImage.TYPE_3BYTE_BGR);
	    ig2 = bi.createGraphics();
	    ig2.fillRect(0, 0, w*PIXELS_SQUARE - 1, h*PIXELS_SQUARE - 1);
	}
	
	/**
	 * It prints a square in the given position
	 * @param x column
	 * @param y file
	 * @param colour colour to be used
	 * @throws BattleshipIOException if the positions is out of limits
	 */
	public void printSquare(int x, int y, Color colour) throws MemoryPracticeIOException {
		if (x<0 || x>=width || y<0 || y>=heigh) {
			throw new MemoryPracticeIOException("Error: wrong position to print a square: ("+ x +","+ y +")");
		}
		
		ig2.setPaint(colour);
		ig2.fill(new Rectangle(x*PIXELS_SQUARE, y*PIXELS_SQUARE, PIXELS_SQUARE, PIXELS_SQUARE));
	}

	/**
	 * Gets the buffered image.
	 *
	 * @return the buffered image
	 */
	BufferedImage getBufferedImage() {
		return bi;
	}
	
	/**
	 * Save file.
	 *
	 * @param file the file
	 * @throws BattleshipIOException the battleship IO exception
	 */
	public void saveFile(File file) throws MemoryPracticeIOException {
	    try {
			ImageIO.write(bi, "GIF", file);
		} catch (IOException e) {
			throw new MemoryPracticeIOException("Error: problem saving file");
		}
	}
	
	
}