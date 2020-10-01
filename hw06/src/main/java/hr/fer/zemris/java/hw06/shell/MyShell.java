package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * MyShell is a simple command-line interpreter that provides a command line
 * user interface for executing basic file operations. To see all supported
 * commands type 'help' and to see what a specific command does type 'help
 * commandName'.
 * 
 * @author Mauro Staver
 *
 */
public class MyShell {

	/**
	 * The main method that gets called when the program starts. The method waits
	 * for the user to enter a command and then executes it. This is repeated until
	 * the program is stopped.
	 * 
	 * @param args command line arguments - not used
	 */
	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("help", new HelpShellCommand());

		Environment env = new EnvironmentImpl(commands);

		env.writeln("Welcome to MyShell v 1.0");
		while (true) {
			env.write(env.getPromptSymbol() + " ");

			String input = readPrompt(env).trim();
			int firstWhitespace = input.indexOf(' ');

			String commandName = input;
			String arguments = "";
			if (firstWhitespace != -1) {
				commandName = input.substring(0, firstWhitespace);
				arguments = input.substring(firstWhitespace + 1);
			}

			ShellCommand command = commands.get(commandName);
			if (command == null) {
				env.writeln("Unsupported command.");
				continue;
			}

			try {
				ShellStatus status = command.executeCommand(env, arguments);

				if (status == ShellStatus.TERMINATE) {
					break;
				}
			} catch (ShellIOException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Terminating shell...");
				break;
			}

		}
	}

	/**
	 * Reads the user prompt from Environment and returns it as a String.
	 * 
	 * @param env Environment used as a source for reading.
	 * @return String - the user entered prompt.
	 */
	private static String readPrompt(Environment env) {
		StringBuilder sb = new StringBuilder();
		String line;
		// while the read line ends with morelinesSymbol
		while ((line = env.readLine()).endsWith(env.getMorelinesSymbol().toString())) {
			env.write(env.getMultilineSymbol() + " ");
			sb.append(line.substring(0, line.length() - 1));
		}
		sb.append(line);
		return sb.toString();
	}

}
