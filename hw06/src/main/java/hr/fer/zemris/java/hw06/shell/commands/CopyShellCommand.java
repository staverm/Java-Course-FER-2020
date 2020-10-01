package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command used for copying files from source to destination. Two
 * arguments are expected: source file name and destination file name(i.e. paths
 * and names). If the second argument is a directory, the file gets copied into
 * that directory using the original file name.
 * 
 * @author Mauro Staver
 *
 */
public class CopyShellCommand implements ShellCommand {

	private String commandName = "copy";
	private List<String> commandDescription = Arrays.asList(
			"Shell command used for copying files from source to destination.",
			"Two arguments are expected: source file name and destination file name(i.e. paths and names).",
			"If the second argument is a directory, the file gets copied into that directory using the original file name.",
			"Example use: 'copy /home/user/source.txt \"/home/user/destination file.txt\"",
			"	: copies the content from 'source.txt' to 'destination file.txt'");

	/**
	 * Copies the file from source to destination. Two arguments are expected:
	 * source file name and destination file name(i.e. paths and names). If the
	 * second argument is a directory, the file gets copied into that directory
	 * using the original file name.
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

		if (argumentList.size() != 2) {
			env.writeln("Expected 2 arguments.");
			return ShellStatus.CONTINUE;
		}

		Path source = Paths.get(argumentList.get(0));
		Path destination = Paths.get(argumentList.get(1));

		if (!Files.exists(source)) {
			env.writeln("The source file doesn't exist.");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(source)) { // source is a directory
			env.writeln(source.toString() + ": is a directory.");
			env.writeln("Only files can be copied using this command.");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(destination)) { // destination is a directory
			destination = destination.resolve(source.getFileName()); // use the file name of the source
		}

		if (Files.isRegularFile(destination)) { // destination is an existing file
			env.writeln("The destination file already exists. Do you want to overwrite it? (YES/NO)");

			while (true) { // repeat until valid input
				String userResponse = env.readLine().trim().toUpperCase();

				if (userResponse.equals("NO")) {
					env.writeln("The file will not be overwritten.");
					env.writeln("0 files copied.");
					return ShellStatus.CONTINUE;
				} else if (userResponse.equals("YES")) {
					break;
				} else {
					env.writeln("Can't recognize: '" + userResponse + "'");
					env.writeln("Please enter 'YES' or 'NO'");
				}
			}
		}

		if (!Files.exists(destination.getParent())) {
			env.writeln("Can't find the destination.");
			return ShellStatus.CONTINUE;
		}

		// COPY
		try (InputStream is = new BufferedInputStream(Files.newInputStream(source))) {
			byte[] buff = new byte[4096];

			OutputStream os = new BufferedOutputStream(Files.newOutputStream(destination));

			while (true) {
				int bytesRead = is.read(buff);
				if (bytesRead < 1) {
					break;
				}

				os.write(buff, 0, bytesRead);
			}

			os.close();

		} catch (IOException ex) {
			env.writeln("IO error during copying.");
			return ShellStatus.CONTINUE;
		}

		env.writeln("1 file copied.");
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
