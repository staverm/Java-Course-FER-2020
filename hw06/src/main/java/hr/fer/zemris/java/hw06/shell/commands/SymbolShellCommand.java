package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command used for printing current symbols and changing current
 * symbols.<br>
 * There are 3 supported symbols:<br>
 * 1. PROMPT - used as a symbol written by the Shell for prompting the user<br>
 * 2. MORELINES - used for multi-line commands as a symbol at the end of each
 * line to indicate to the Shell to expect more lines.<br>
 * 3. MULTILINE - used for multi-line commands as a symbol writen by the Shell
 * at the beginning of each new line of that command.
 * 
 * 
 * @author Mauro Staver
 *
 */
public class SymbolShellCommand implements ShellCommand {

	private String commandName = "symbol";
	private List<String> commandDescription = Arrays.asList(
			"Shell command used for printing current symbols and changing current symbols.",
			"There are 3 supported symbols:",
			"1. PROMPT - used as a symbol written by the Shell for prompting the user",
			"2. MORELINES - used for multi-line commands as a symbol at the end of each line to indicate"
					+ " to the Shell to expect more lines.",
			"3. MULTILINE - used for multi-line commands as a symbol writen by the Shell at the beginning"
					+ " of each new line of that command.",
			"Example use:", "'symbol PROMPT' : prints the current PROMPT symbol",
			"'symbol MULTILINE !' : changes the current MULTILINE symbol to '!'");

	/**
	 * Prints the current specified symbol or changes it into a new one, if a new
	 * symbol is given as an argument.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList;

		try {
			argumentList = Util.parseArguments(arguments);
		} catch (IllegalArgumentException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (argumentList.size() == 1) {
			switch (argumentList.get(0)) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
				break;
			case "MORELINES":
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
				break;
			default:
				env.writeln("There is no symbol for '" + argumentList.get(0) + "'");
				break;
			}

		} else if (argumentList.size() == 2) {
			if(argumentList.get(1).length() != 1) {
				env.writeln("Expected a char for a new symbol.");
				return ShellStatus.CONTINUE;
			}
			
			char newSymbol = argumentList.get(1).charAt(0);

			switch (argumentList.get(0)) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + newSymbol + "'");
				env.setPromptSymbol(newSymbol);
				break;
			case "MORELINES":
				env.writeln(
						"Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + newSymbol + "'");
				env.setMorelinesSymbol(newSymbol);
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is changed from '" + env.getMultilineSymbol() + "' to '" + newSymbol
						+ "'");
				env.setMultilineSymbol(newSymbol);
				break;
			default:
				env.writeln("There is no symbol for '" + argumentList.get(0) + "'");
				break;
			}

		} else { // wrong number of arguments
			env.writeln("Expected 1 or 2 arguments.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
