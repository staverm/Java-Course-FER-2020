package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command that take a single argument: path to directory and creates the
 * appropriate directory structure if it doesn't already exist.
 * 
 * @author Mauro Staver
 *
 */
public class MkdirShellCommand implements ShellCommand {

	private String commandName = "mkdir";
	private List<String> commandDescription = Arrays.asList(
			"Shell command that take a single argument: path to directory and creates the appropriate"
					+ " directory structure if it doesn't already exist.",
			"Example: 'mkdir /home/user/dir1/dir2' : creates the /home/user/dir1/dir2 structure if it doesn't already exist.");

	/**
	 * Creates the appropriate directory structure(if it doesn't already exist) of a
	 * directory specified as an argument.
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

		File newDir = new File(argumentList.get(0));

		if (newDir.mkdirs()) {
			env.writeln("The directory was created.");
		} else {
			env.writeln("The directory could not be created or already exists.");
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(commandDescription);
	}

}
