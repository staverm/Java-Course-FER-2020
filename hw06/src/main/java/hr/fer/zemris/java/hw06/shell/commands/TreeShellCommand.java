package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command that takes a single argument: path to directory and recursively
 * lists contents of that directory in a tree-like format.
 * 
 * @author Mauro Staver
 *
 */
public class TreeShellCommand implements ShellCommand {

	private String commandName = "tree";
	private List<String> commandDescription = Arrays.asList(
			"Shell command that takes a single argument: path to directory and recursively lists contents"
					+ " of that directory in a tree-like format.",
			"Example: 'tree /home/user/dir' : recursively lists contents of /home/user/dir");

	/**
	 * Recursively lists contents of the specified directory in a tree-like format.
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

		if (argumentList.size() != 1) {
			env.writeln("Expected 1 argument.");
			return ShellStatus.CONTINUE;
		}

		File directory = new File(argumentList.get(0));

		if (!directory.exists()) {
			env.writeln("The specified directory doesn't exist.");
			return ShellStatus.CONTINUE;
		}

		printTree(directory, 0, env);

		return ShellStatus.CONTINUE;
	}

	/**
	 * Recursively prints the directory tree.
	 * 
	 * @param directory root directory.
	 * @param level     level of the tree - each tree level shifts output two chars
	 *                  to the right.
	 */
	private void printTree(File directory, int level, Environment env) {
		for (int i = 0; i < level; i++) {
			env.write("  "); // shift output
		}

		if (directory.isDirectory()) {
			env.writeln("[" + directory.getName() + "]");
			
			
			File[] children = directory.listFiles();
			
			if(children != null) {
				for (File file : children) {
					printTree(file, level + 1, env);
				}
			}
		} else {
			env.writeln(directory.getName()); // print file name
		}
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
