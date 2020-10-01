package hr.fer.zemris.java.tecaj_13.dao.jpa;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Class that handles a thread local EntityManager object.
 * 
 * @author staver
 *
 */
public class JPAEMProvider {

	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Returns a EntityManager object local to this thread. NOTE: the method begins
	 * the transaction before returning the EntityManager object so you don't have
	 * to do it manually.
	 * 
	 * @return EntityManager object local to this thread.
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Closes the EntityManager object local to this thread. First tries to commit
	 * the transaction(so you don't have to do it manually) and then closes the
	 * EntityManager object.
	 * 
	 * @throws DAOException if an exception occurs
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

}