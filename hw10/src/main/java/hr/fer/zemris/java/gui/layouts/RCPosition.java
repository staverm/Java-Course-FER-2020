package hr.fer.zemris.java.gui.layouts;

/**
 * Class that models a position in a grid. It holds two read only attributes:
 * row and column.
 * 
 * @author staver
 *
 */
public class RCPosition {

	private int row; // row number
	private int column; // column number

	/**
	 * Constructs and initializes a new RCPosition using the given values.
	 * 
	 * @param row    row number
	 * @param column column number
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Returns the row number.
	 * 
	 * @return the row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column number.
	 * 
	 * @return the column number
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Parses the given string into a new RCPosition object and returns it. Example
	 * of a parsable string: "3, 7".
	 * 
	 * @param text string to be parsed
	 * @return a new RCPosition object created by parsing the given string.
	 * @throws IllegalArgumentException if unable to parse
	 */
	public static RCPosition parse(String text) {
		text = text.replaceAll("\\s+", ""); // remove whitespace
		String[] textSplit = text.split(",");

		int rowNumber, columnNumber;
		try {
			rowNumber = Integer.parseInt(textSplit[0]);
			columnNumber = Integer.parseInt(textSplit[1]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException(ex);
		}

		return new RCPosition(rowNumber, columnNumber);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null)
			return false;

		if (this.getClass() != o.getClass())
			return false;

		RCPosition position = (RCPosition) o;
		return row == position.row && column == position.column;
	}

}
