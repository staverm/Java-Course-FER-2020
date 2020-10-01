package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Form used to add new or edit existing blog entries.
 * 
 * @author staver
 *
 */
public class BlogEntryForm {
	
	private String title; //entry title
	private String text; //entry text
	
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
		text = prepare(req.getParameter("text"));
		title = prepare(req.getParameter("title"));
	}

	/**
	 * Fills the given BlogEntry object with data from this form.
	 * 
	 * @param entry blog entry to be filled
	 */
	public void fillBlogEntry(BlogEntry entry) {
		entry.setText(text);
		entry.setTitle(title);
	}

	/**
	 * Fills this form with data from given blog entry.
	 * 
	 * @param entry blog entry used to fill this form
	 */
	public void fillFromEntry(BlogEntry entry) {
		title = entry.getTitle();
		text = entry.getText();
	}

	/**
	 * Validates this form. It is expected that this form is filled with data that
	 * needs to be validated. Checks data semantics and if needed registers
	 * appropriate errors in the error map.
	 */
	public void validate() {
		if (title.isEmpty()) {
			errors.put("title", "Title can't be empty!");
		}

		if (text.isEmpty()) {
			errors.put("text", "Text can't be empty!");
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
	 * Entry title getter.
	 * @return entries' title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Entry title setter
	 * @param title title to be set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Entry text getter.
	 * @return entries' text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Entry text setter.
	 * @param text text to be set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
}
