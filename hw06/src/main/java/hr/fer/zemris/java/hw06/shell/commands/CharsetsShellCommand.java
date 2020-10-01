package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command that lists names of supported charsets.
 * It takes no arguments.
 * 
 * @author Mauro Staver
 *
 */
public class CharsetsShellCommand implements ShellCommand{
	
	private String commandName = "charsets";
	private List<String> commandDescription = Arrays.asList(
			"Shell command that lists names of supported charsets.",
			"It takes no arguments.", 
			"Example use: 'charsets' : lists names of supported charsets.");

	
	/**
	 * Lists names of supported charsets.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.length() != 0) {
			env.writeln("No arguments expected.");
			return ShellStatus.CONTINUE;
		}
		
		for(String charset : Charset.availableCharsets().keySet()) {
			System.out.println(charset);
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
