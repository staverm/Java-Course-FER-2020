package hr.fer.zemris.java.tecaj_13.web.forms;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Form used to add new blog users. This form is also used for the login (but
 * only some fields are used).
 * 
 * @author staver
 *
 */
public class BlogUserForm {

	private String firstName; // user first name
	private String lastName; // user last name
	private String nick; // user nickname
	private String email; // user email
	private String password; // user password

	Map<String, String> errors = new HashMap<>(); // error map

	/**
	 * Returns an error mapped to the given string.
	 * 
	 * @param name string used as key in the error map
	 * @return error mapped to given string or null if no mapping exists
	 */
	public String getError(String name) {
		return errors.get(name);
	}

	/**
	 * Returns true if at least one error exists.
	 * 
	 * @return true if at least one error exists, false otherwise.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Returns true if there exists an error mapped to the given string.
	 * 
	 * @param name string used as key in the error map
	 * @return true if there exists an error mapped to the given string, false
	 *         otherwise.
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Fills this form from given {@link HttpServletRequest} parameters.
	 * 
	 * @param req request object with parameters
	 * @throws ServletException if exception occurs
	 */
	public void fillFromHttpRequest(HttpServletRequest req) throws ServletException {
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.nick = prepare(req.getParameter("nick"));
		this.email = prepare(req.getParameter("email"));
		this.password = req.getParameter("password");
	}

	/**
	 * Fills the given BlogUser object with data from this form.
	 * 
	 * @param user user to be filled
	 */
	public void fillBlogUser(BlogUser user) {
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(nick);
		user.setPasswordHash(calcHexHash(prepare(password)));
	}

	/**
	 * Fills this form with data from given user (everything except password because
	 * BlogUser stores hashed password).
	 * 
	 * @param user user used to fill this form
	 */
	public void fillFromUser(BlogUser user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.nick = user.getNick();
		this.email = user.getEmail();
	}

	/**
	 * Validates this form. It is expected that this form is filled with data that
	 * needs to be validated. Checks data semantics and if needed registers
	 * appropriate errors in the error map.
	 */
	public void validateRegistration() {
		errors.clear();

		if (nick.isEmpty()) {
			errors.put("nick", "Nickname is required!");
		} else { // check if nick already exists

			BlogUser userEntry = DAOProvider.getDAO().getBlogUser(nick);

			if (userEntry != null) {
				errors.put("nick", "Nickname already exists. Please choose a different nickname.");
			}
		}

		if (password.isEmpty()) {
			errors.put("password", "Password is required!");
		}

		if (firstName.isEmpty()) {
			errors.put("firstName", "First name is required!");
		}
		if (lastName.isEmpty()) {
			errors.put("lastName", "Last name is required!");
		}
		if (email.isEmpty()) {
			errors.put("email", "email is required!");
		}

		if (firstName.isEmpty()) {
			errors.put("firstName", "First name is required!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "Email has the wrong format.");
			}
		}
	}

	/**
	 * Validates this form. It is expected that this form is filled with data that
	 * needs to be validated. Checks data semantics and if needed registers
	 * appropriate errors in the error map.
	 */
	public void validateLogin() {
		errors.clear();

		boolean err = false;
		if (nick.isEmpty()) {
			errors.put("nick", "Nickname is required!");
			err = true;
		}

		if (password.isEmpty()) {
			errors.put("password", "Password is required!");
			err = true;
		}

		BlogUser user = DAOProvider.getDAO().getBlogUser(nick);
		if (!err && (user == null || !user.getPasswordHash().equals(calcHexHash(prepare(password))))) {
			errors.put("password", "Wrong nickname or password. Try again!");
		}

	}

	/**
	 * Returns a hex encoded hash value (SHA-256 is used).
	 * 
	 * @param s String to be encoded
	 * @return a hex encoded hash value (SHA-256 is used)
	 */
	private String calcHexHash(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
			return byteToHex(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper method that converts <code>null</code> string into empty strings,
	 * which is more appropriate for use on the web.
	 * 
	 * @param s string
	 * @return trimmed given string if it's not null, otherwise empty string
	 */
	private String prepare(String s) {
		if (s == null)
			return "";
		return s.trim();
	}

	/**
	 * Converts the specified byte array into a String containing the bytes hex
	 * value. Each byte in the array is converted into a String containing its hex
	 * value and these Strings are then concatenated together.
	 * 
	 * @param byteArray byte array to convert into String.
	 * @return String containing the
	 */
	public static String byteToHex(byte[] byteArray) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			sb.append(String.format("%02x", byteArray[i]));
		}

		return sb.toString();
	}

	/**
	 * First name getter.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * First name setter.
	 * 
	 * @param firstName first name to be set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Last name getter.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Last name setter.
	 * 
	 * @param lastName last name to be set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Nickname getter
	 * 
	 * @return nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Nickname setter.
	 * 
	 * @param nick nickname to be set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Email getter.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Email setter.
	 * 
	 * @param email email to be set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
