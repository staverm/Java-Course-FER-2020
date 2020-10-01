package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class that provides an EntityManagerFactory object. This class is used to
 * avoid opening multiple EntityManagerFactory objects. In our program the
 * EntityManagerFactory object is set during servlet context creation.
 * 
 * @author staver
 *
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;

	/**
	 * EntityManagerFactory object getter.
	 * 
	 * @return EntityManagerFactory object.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * EntityManagerFactory object setter.
	 * 
	 * @param emf object to be set
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}