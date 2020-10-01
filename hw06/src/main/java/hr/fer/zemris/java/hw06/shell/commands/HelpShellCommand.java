package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command used to display information about built-in commands. If started
 * with no arguments, it lists names of all supported commands. If started with
 * a single argument, it prints the name and the description of selected
 * command.
 * 
 * @author Mauro Staver
 *
 */
public class HelpShellCommand implements ShellCommand {

	private String commandName = "help";
	private List<String> commandDescription = Arrays.asList(
			"Shell command used to display information about built-in commands.",
			"If started with no arguments, it lists names of all supported commands.",
			"If started with a single argument, it prints the name and the description of selected command.",
			"Example use: 'help cat' : Prints the name and description of the 'cat' command");

	/**
	 * If started with no arguments, it lists names of all supported commands. If
	 * started with a single argument, it prints the name and the description of
	 * selected command.
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

		if (argumentList.size() == 0) {
			for (String key : env.commands().keySet()) {
				env.writeln(key);
			}

		} else if (argumentList.size() == 1) {
			String commandName = argumentList.get(0);

			ShellCommand command = env.commands().get(commandName);
			if (command == null) {
				env.writeln("The command '" + commandName + "' doesn't exist.");
				return ShellStatus.CONTINUE;
			}

			env.writeln(commandName);
			for (String line : command.getCommandDescription()) {
				env.writeln(line);
			}

		} else {
			env.writeln("Too many arguments!");
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
