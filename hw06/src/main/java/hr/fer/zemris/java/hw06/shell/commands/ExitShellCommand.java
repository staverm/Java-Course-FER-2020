package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command used for exiting and terminating the Shell.
 * 
 * @author Mauro Staver
 *
 */
public class ExitShellCommand implements ShellCommand {

	private String commandName = "exit";
	private List<String> commandDescription = Arrays.asList(
			"Shell command used for exiting and terminating the Shell.",
			"Takes no arguments.", 
			"Example use: 'exit' : terminates the Shell.");

	/**
	 * Exits and terminates MyShell.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			env.writeln("No arguments expected.");
			return ShellStatus.CONTINUE;
		}

		env.writeln("Goodbye!");
		return ShellStatus.TERMINATE;
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
