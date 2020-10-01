package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Interface that represents the environment used for interaction with the user.
 * The environment serves as an intermediary for interaction between the user and
 * the program.
 * 
 * @author Mauro Staver
 *
 */
public interface Environment {

	/**
	 * Reads from Environment's input stream until a new line char is detected.
	 * Returns a String with the read text.
	 * 
	 * @return String - the read line
	 * @throws ShellIOException if unable to read line from the stream
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes the specified String to Environment's output stream.
	 * 
	 * @param text String to write
	 * @throws ShellIOException if unable to write to the stream
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes the specified String to Environment's output stream and adds a new
	 * line char at the end.
	 * 
	 * @param text String to write
	 * @throws ShellIOException if unable to write to the stream
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns an unmodifiable map of all supported commands of MyShell.
	 * Map keys are command names, and values are actual ShellCommand objects.
	 * 
	 * @return unmodifiable map of all supported commands of MyShell.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns the current MULTILINE symbol.
	 * 
	 * @return the current MULTILINE symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the MULTILINE symbol to the specified symbol.
	 * 
	 * @param symbol symbol to set the MULTILINE symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns the current PROMPT symbol.
	 * 
	 * @return the current PROMPT symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets the PROMPT symbol to the specified symbol.
	 * 
	 * @param symbol symbol to set the PROMPT symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns the current MORELINES symbol.
	 * 
	 * @return the current MORELINES symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the MORELINES symbol to the specified symbol.
	 * 
	 * @param symbol symbol to set the MORELINES symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
