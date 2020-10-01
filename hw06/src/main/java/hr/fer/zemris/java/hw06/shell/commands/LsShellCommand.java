package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Shell command that takes a single argument: path to directory and prints a
 * non-recursive directory listing of that directory.
 * 
 * @author Mauro Staver
 *
 */
public class LsShellCommand implements ShellCommand {

	private String commandName = "ls";
	private List<String> commandDescription = Arrays.asList(
			"Shell command that takes a single argument: path to directory and prints a non-recursive"
					+ " directory listing of that directory.",
			"Example: 'ls /home/user/dir' : writes a directory listing of directory at /home/user/dir");

	/**
	 * Prints a non-recursive directory listing of a directory specified as an
	 * argument.
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

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(p)) {

			for (Path entry : stream) {
				sb.setLength(0); // reset StringBuilder

				BasicFileAttributeView faView = Files.getFileAttributeView(entry, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

				sb.append(Files.isDirectory(entry) ? 'd' : '-');
				sb.append(Files.isReadable(entry) ? 'r' : '-');
				sb.append(Files.isWritable(entry) ? 'w' : '-');
				sb.append(Files.isExecutable(entry) ? 'x' : '-');
				sb.append(' ');
				sb.append(String.format("%10d", attributes.size()));
				sb.append(' ');
				sb.append(formattedDateTime);
				sb.append(' ');
				sb.append(entry.getFileName().toString());

				env.writeln(sb.toString());
			}
		} catch (IOException e) {
			env.writeln("No directories found at the specified path.");
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
