package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command that opens the specified file and writes its content to the
 * Environment. The first argument is path to some file and is mandatory. The
 * second argument is charset name that should be used to interpret chars from
 * bytes. If not provided, a default platform charset is used.
 * 
 * @author Mauro Staver
 *
 */
public class CatShellCommand implements ShellCommand {

	private String commandName = "cat";
	private List<String> commandDescription = Arrays.asList(
			"Shell command that opens the given file and writes its content to the console.",
			"The first argument is path to some file and is mandatory.",
			"The second argument is charset name that should be used to interpret chars from bytes.",
			"If not provided, a default platform charset is used.",
			"Example use: 'cat /home/user/file.txt UTF-16' : prints the content of file.txt to"
					+ " the console using UTF-16 charset.");

	/**
	 * Opens the specified file and writes its content to the Environment using the
	 * specified charset. If not provided, a default platform charset is used.
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

		if (argumentList.size() < 1 || argumentList.size() > 2) {
			env.writeln("Expected 1 or 2 arguments.");
			return ShellStatus.CONTINUE;
		}

		Path p = Paths.get(argumentList.get(0));
		Charset charset = Charset.defaultCharset();

		if (Files.isDirectory(p)) {
			env.writeln(p.toString() + ": is a directory.");
			return ShellStatus.CONTINUE;
		}

		if (argumentList.size() == 2) { // optional argument
			try {
				charset = Charset.forName(argumentList.get(1));
			} catch (Exception ex) {
				env.writeln(argumentList.get(1) + ": is an unsupported charset.");
				return ShellStatus.CONTINUE;
			}
		}

		try (BufferedReader br = Files.newBufferedReader(p, charset)) {
			br.lines().forEach(s -> env.writeln(s));
		} catch (IOException ex) {
			env.writeln("Unable to read file.");
		} catch (UncheckedIOException ex) {
			env.writeln("Unable to read file with the current charset.");
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
