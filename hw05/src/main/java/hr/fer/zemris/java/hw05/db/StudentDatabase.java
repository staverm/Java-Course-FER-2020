package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that implements a student database. It stores student records and
 * implements basic methods for interacting with the database.
 * 
 * @author Mauro Staver
 *
 */
public class StudentDatabase {

	private List<StudentRecord> recordList = new ArrayList<>(); // list of records
	private Map<String, StudentRecord> map = new HashMap<>(); // jmbag -> StudentRecord

	/**
	 * Constructs and initializes a new StudentDatabase from the specified String
	 * array. Each element of the string array represents one student record. The
	 * method parses each element of the string array and initializes this object
	 * using the parsed information. For the information on the supported format of
	 * the strings consult homework 5 text.
	 * 
	 * @param records array of Strings used for initializing a new StudentDatabase
	 *                object.
	 */
	public StudentDatabase(List<String> records) {
		for (int i = 0; i < records.size(); ++i) {
			String recordString = records.get(i).trim().replaceAll("\\s+", " "); // trim and reduce multiple whitespace
																				// signs to one
			String[] split = recordString.split(" "); // split by spaces

			String jmbag = split[0];
			String firstName, lastName;
			int finalGrade;

			if (split.length == 4) { // first name doesn't have a space
				lastName = split[1];
				firstName = split[2];
				finalGrade = Integer.parseInt(split[3]); // may throw NumberFormatException
			} else if (split.length == 5) { // first name has a space
				lastName = split[1] + " " + split[2];
				firstName = split[3];
				finalGrade = Integer.parseInt(split[4]); // may throw NumberFormatException
			} else {
				throw new IllegalArgumentException("Invalid input for student record."); // everything else is invalid
			}

			if (map.containsKey(jmbag) || finalGrade < 1 || finalGrade > 5) { // illegal input, terminate
				if (map.containsKey(jmbag)) {
					System.out.println("Duplicate jmbag in input. Terminate.");
				} else {
					System.out.println("FinalGrade is not between 1 and 5. Terminate.");
				}
				System.exit(-1); // terminate
			}

			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);

			map.put(split[0], record);
			recordList.add(record);
		}
	}

	/**
	 * Returns the StudentRecord associated with the specified jmbag. The jmbag is
	 * used as a key.
	 * 
	 * @param jmbag the jmbag whose associated StudentRecord is to be returned
	 * @return the StudentRecord to which the specified jmbag is mapped, or null
	 *         there is no mapping for the jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return map.get(jmbag);
	}

	/**
	 * Returns a list of student records which satisfy the specified filter.
	 * 
	 * @param filter Filter object used to filter student records
	 * @return a new List<StudentRecord> where all elements satisfy the specified
	 *         filter.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredRecords = new ArrayList<>();
		for (StudentRecord record : recordList) {
			if (filter.accepts(record)) {
				filteredRecords.add(record);
			}
		}
		return filteredRecords;
	}
}
