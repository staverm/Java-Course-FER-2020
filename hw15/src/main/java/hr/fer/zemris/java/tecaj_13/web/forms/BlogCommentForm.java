package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;

/**
 * Form used to add comments to blog entries.
 * 
 * @author staver
 *
 */
public class BlogCommentForm {

	private String message; // comment message

	Map<String, String> errors = new HashMap<>(); //map of errors

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
		message = prepare(req.getParameter("message"));
	}

	/**
	 * Fills the given BlogComment object with data from this form.
	 * 
	 * @param comment blog comment to be filled
	 */
	public void fillBlogComment(BlogComment comment) {
		comment.setMessage(message);
	}

	/**
	 * Fills this form with data from given blog comment.
	 * 
	 * @param comment blog comment to be filled
	 */
	public void fillFromComment(BlogComment comment) {
		message = comment.getMessage();
	}

	/**
	 * Validates this form. It is expected that this form is filled with data that
	 * needs to be validated. Checks data semantics and if needed registers
	 * appropriate errors in the error map.
	 */
	public void validate() {
		if (message.isEmpty()) {
			errors.put("message", "Comment can't be empty!");
		}
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
	 * Comment message getter.
	 * 
	 * @return comment message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Comment massage setter.
	 * 
	 * @param message message to be set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
