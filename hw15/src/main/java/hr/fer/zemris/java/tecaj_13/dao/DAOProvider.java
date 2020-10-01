package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Singleton class that returns a service provider that provides access to the
 * persistence subsystem.
 * 
 * @author staver
 *
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();

	/**
	 * DAO getter.
	 * 
	 * @return object which encapsulates persistence subsystem access.
	 */
	public static DAO getDAO() {
		return dao;
	}

}