package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Interface used to communicate with the persistence subsystem.
 * 
 * @author staver
 *
 */
public interface DAO {

	/**
	 * Retrieves entry with given <code>id</code>. If no such entry exists, returns null.
	 * 
	 * @param entry id
	 * @return entry or null if no entry with given id exists
	 * @throws DAOException if exception occurs while accessing the persistence subsystem
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Returns BlogUser with given nickname. If no such user exists, returns null.
	 * 
	 * @param nick BlogUser nickname
	 * @return BlogUser with given nickname or null if no such user exists
	 * @throws DAOException if exception occurs while accessing the persistence subsystem
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
	
	/**
	 * Returns a list of all registered blog users.
	 * 
	 * @return list of all registered blog users
	 * @throws DAOException if exception occurs while accessing the persistence subsystem
	 */
	public List<BlogUser> getAllBlogUsers() throws DAOException;
	
	/**
	 * Returns a list of blog entires whose author is the given blog user.
	 * @param author author of the blog entries to be returned
	 * @return list of blog entires whose author is the given blog user
	 * @throws DAOException if exception occurs while accessing the persistence subsystem
	 */
	public List<BlogEntry> getBlogEntries(BlogUser author) throws DAOException;
}