package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class that implements methods for parsing arguments of Shell
 * commands.
 * 
 * @author Mauro Staver
 *
 */
public class Util {

	/**
	 * Parses the specified string into a list of arguments and returns it.
	 * 
	 * @param str String to be parsed.
	 * @return List of String where each String is one argument.
	 * @throws IllegalArgumentException - if the specified String could not be parsed.
	 */
	public static List<String> parseArguments(String str) {
		List<String> list = new ArrayList<>();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			sb.setLength(0); // reset stringBuilder

			// remove leading whitespace
			while (i < str.length()) {
				if (Character.isWhitespace(str.charAt(i))) {
					i++;
				} else {
					break;
				}
			}

			if (str.charAt(i) == '\"') { // STRING STATE
				i++; // consume the opening "
				while (i < str.length() && str.charAt(i) != '\"') { // read until EOF or the closing "
					if (str.charAt(i) == '\\') {
						if (i + 1 < str.length() && (str.charAt(i + 1) == '\"' || str.charAt(i + 1) == '\\')) {
							i++; // escape
						}
					}

					sb.append(str.charAt(i));
					i++;
				}

				i++; // consume the closing "
				if (i < str.length() && str.charAt(i) != ' ') {
					throw new IllegalArgumentException("Invalid string literal. Characters found after the ending \"");
				}

				list.add(sb.toString());
				continue;
			}
			

			// read until EOF or opening " or whitespace
			while (i < str.length() && str.charAt(i) != '\"' && str.charAt(i) != ' ') {
				sb.append(str.charAt(i));
				i++;
			}
			list.add(sb.toString());

		}

		return list;
	}
}
