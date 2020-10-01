package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * Implementation of Environment using standard input/output as means for
 * communication between the user and the program.
 * 
 * @author Mauro Staver
 *
 */
public class EnvironmentImpl implements Environment {

	private char promptSymbol = '>';
	private char multilineSymbol = '|';
	private char morelinesSymbol = '\\';
	private SortedMap<String, ShellCommand> commands; // supported commands by this Environment
	Scanner sc = new Scanner(System.in); // used for reading and writing to console

	/**
	 * Constructs and initializes a new EnvironmentImpl with the specified map of
	 * commands.
	 * 
	 * @param commands map of commands that this Environment will support
	 */
	public EnvironmentImpl(SortedMap<String, ShellCommand> commands) {
		this.commands = commands;
	}
	
	/**
	 * Reads from the standard input until a new line char is detected. Returns a String with the read text.
	 */
	@Override
	public String readLine() throws ShellIOException {
		try {
			String s = sc.nextLine();
			return s;
		} catch (NoSuchElementException ex) {
			throw new ShellIOException(ex.getMessage());
		}
	}
	
	/**
	 * Writes the specified String to the standard output.
	 */
	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}
	
	/**
	 * Writes the specified String to the standard output and adds a new line char at the end.
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}
