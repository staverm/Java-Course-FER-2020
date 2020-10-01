package hr.fer.zemris.java.hw05.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program for loading and querying a database. The program first loads and
 * parses the database from a database.txt file(located in the current
 * directory). The user can then type queries in the console(similar to SQL but
 * with less functionality) and the program will generate corresponding tables.
 * 
 * @author Mauro Staver
 *
 */
public class StudentDB {

	private static StudentDatabase db; //student database

	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		db = new StudentDatabase(lines); // initialize database

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		while (true) {
			String s = sc.nextLine();
			if (s.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}

			List<StudentRecord> records = null;
			try {
				records = selectFiltered(s);
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Try again!");
				continue;
			}

			List<String> output = format(records);
			output.forEach(System.out::println);
		}

	}

	/**
	 * Formats the specified List of student records into a table. Returns a List of
	 * strings where each string represents a row in the table.
	 * 
	 * @param records List<StudentRecord> to be formatted.
	 * @return List<String> where each string represents a row in the table.
	 */
	private static List<String> format(List<StudentRecord> records) {
		int maxJmbag = 0, maxLastName = 0, maxFirstName = 0; // max lengths used for column width
		// find max lengths for jmbag, lastName and firstName
		for (StudentRecord record : records) {
			if (record.getJmbag().length() > maxJmbag) {
				maxJmbag = record.getJmbag().length();
			}
			if (record.getFirstName().length() > maxFirstName) {
				maxFirstName = record.getFirstName().length();
			}
			if (record.getLastName().length() > maxLastName) {
				maxLastName = record.getLastName().length();
			}
		}

		List<String> output = new ArrayList<>();

		if (records.size() > 0) {
			output.add(printBorder(maxJmbag, maxLastName, maxFirstName)); // add top border

			for (StudentRecord record : records) {
				String format = "| %-" + maxJmbag + "s | %-" + maxLastName + "s | %-" + maxFirstName + "s | %d |";
				output.add(String.format(format, record.getJmbag(), record.getLastName(), record.getFirstName(),
						record.getFinalGrade()));
			}

			output.add(printBorder(maxJmbag, maxLastName, maxFirstName)); // add bottom border
		}
		output.add("Records selected: " + records.size());
		return output;
	}

	/**
	 * Returns the border of the table as a string.
	 * 
	 * @param column1Width int for the wanted width of column1
	 * @param column2Width int for the wanted width of column2
	 * @param column3Width int for the wanted width of column3
	 */
	private static String printBorder(int column1Width, int column2Width, int column3Width) {
		StringBuilder sb = new StringBuilder();
		sb.append('+');
		fillWithEqualSigns(sb, column1Width + 2);
		sb.append('+');
		fillWithEqualSigns(sb, column2Width + 2);
		sb.append('+');
		fillWithEqualSigns(sb, column3Width + 2);
		sb.append('+');
		fillWithEqualSigns(sb, 3);
		sb.append('+');

		return sb.toString();
	}

	/**
	 * Appends to the specified StringBuilder '=' numberOfSigns times, where
	 * numberOfSigns is the second parameter.
	 * 
	 * @param sb            StringBuilder to append to
	 * @param numberOfSigns int which represents how many times to append
	 */
	private static void fillWithEqualSigns(StringBuilder sb, int numberOfSigns) {
		for (int i = 0; i < numberOfSigns; i++) {
			sb.append('=');
		}
	}

	/**
	 * Returns a list of student records which satisfy the query given as a string
	 * argument.
	 * 
	 * @param line query in String format
	 * @return List<StudentRecord> of student records which satisfy the query given
	 *         as a string argument.
	 * @throws IllegalArgumentException if the query has invalid format.
	 */
	private static List<StudentRecord> selectFiltered(String line) {
		if (line.startsWith("query")) {
			String query = line.substring(5); // remove the prefix "query"
			QueryParser parser = new QueryParser(query);

			if (parser.isDirectQuery()) { // quick retrieval for direct queries
				List<StudentRecord> list = new ArrayList<>();
				list.add(db.forJMBAG((parser.getQueriedJMBAG())));
				return list;
			}
			
			return db.filter(new QueryFilter(parser.getQuery()));
		} else {
			throw new IllegalArgumentException("Invalid command.");
		}
	}

}
