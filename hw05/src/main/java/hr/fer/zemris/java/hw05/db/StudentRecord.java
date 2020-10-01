package hr.fer.zemris.java.hw05.db;

/**
 * Represents a record of a student. There can not exist multiple records for
 * the same student.
 * 
 * @author Mauro Staver
 *
 */
public class StudentRecord {

	private String jmbag; // student's jmbag
	private String firstName; // student's first name
	private String lastName; // student's last name
	private int finalGrade; // student's final grade

	/**
	 * Constructs and initializes a new StudentRecord with the specified values
	 * 
	 * @param jmbag      Student's jmbag
	 * @param firstName  Student's first name
	 * @param lastName   Student's last name
	 * @param finalGrade Student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Returns the student's jmbag.
	 * 
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns the student's first name.
	 * 
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the student's last name.
	 * 
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the student's final grade.
	 * 
	 * @return student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

}
