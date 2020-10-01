package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command that produces a hex-output from the specified file. It takes
 * one argument: a path to file. It reads bytes from the file and prints them in
 * hex format and as characters.
 * 
 * @author Mauro Staver
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	private String commandName = "hexdump";
	private List<String> commandDescription = Arrays.asList(
			"Shell command that produces a hex-output from the specified file.",
			"It takes one argument: a path to file.",
			"It reads bytes from the file and prints them in hex format and as characters.",
			"Example use: 'hexdump /home/user/dir/file.txt' : produces a hex-output from /home/user/dir/file.txt");

	/**
	 * Produces a hex-output from the specified file. It takes one argument: a path
	 * to file. It reads bytes from the file and prints them in hex format and as
	 * characters.
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

		Path p = Paths.get(argumentList.get(0));

		try (InputStream is = new BufferedInputStream(Files.newInputStream(p))) {
			int bufferSize = 16;
			byte[] buff = new byte[bufferSize];

			int byteCounter = 0;
			StringBuilder sb = new StringBuilder();

			while (true) {
				sb.setLength(0); // reset StringBuilder

				int bytesRead = is.read(buff);
				if (bytesRead < 1) {
					break;
				}

				sb.append(String.format("%08X:", bufferSize * byteCounter));

				// FIRST HALF
				for (int i = 0; i < bufferSize / 2; i++) {
					if (i < bytesRead) {
						sb.append(String.format(" %02X", buff[i]));
					} else {
						sb.append("   ");
					}
				}

				sb.append("|");

				// SECOND HALF
				for (int i = bufferSize / 2; i < bufferSize; i++) {
					if (i < bytesRead) {
						sb.append(String.format("%02X ", buff[i]));
					} else {
						sb.append("   ");
					}
				}

				sb.append("| ");
				
				// appending chars
				for (int i = 0; i < bytesRead; i++) {
					if (buff[i] < 32 || buff[i] > 127) {
						sb.append(".");
					} else {
						sb.append((char) buff[i]);
					}
				}

				env.writeln(sb.toString());
				byteCounter++;
			}

		} catch (IOException ex) {
			env.writeln("Can't read from the specified file.");
			return ShellStatus.CONTINUE;
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
