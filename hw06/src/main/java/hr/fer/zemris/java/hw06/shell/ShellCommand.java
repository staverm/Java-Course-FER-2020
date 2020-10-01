package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface that represents a Shell command. The command can be executed by
 * calling the method executeCommand. The interface also provides methods for
 * getting information(description, name) about this command.
 * 
 * @author Mauro Staver
 *
 */
public interface ShellCommand {

	/**
	 * 
	 * Executes this command.
	 * 
	 * @param env       Environment used for interacting with the
	 *                  user(reading/writing)
	 * @param arguments String containing the arguments of this command i.e.
	 *                  everything typed after the command name
	 * @return ShellStatus: TERMINATE if the Shell should terminate, CONTINUE
	 *         otherwise.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the name of this command.
	 * 
	 * @return String - the name of the command.
	 */
	String getCommandName();

	/**
	 * Returns a description(usage instructions) for this command as an unmodifiable List of
	 * Strings where each String is one line of the description.
	 * 
	 * @return unmodifiable List of Strings where each String is one line of the description of
	 *         this command.
	 */
	List<String> getCommandDescription();

}
