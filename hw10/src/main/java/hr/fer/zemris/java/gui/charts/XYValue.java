package hr.fer.zemris.java.gui.charts;

/**
 * Class that models (x,y) pair.
 * 
 * @author staver
 *
 */
public class XYValue {
	
	private int x;
	private int y;
	
	/**
	 * Constructs a new XYValue object using the given values.
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x value of this object.
	 * 
	 * @return the x value of this object
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y value of this object.
	 * 
	 * @return the y value of this object
	 */
	public int getY() {
		return y;
	}

}
