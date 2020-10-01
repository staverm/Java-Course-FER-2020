package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;
import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * DAO implementation using JPA.
 * 
 * @author staver
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogUser as b where b.nick=:nick").setParameter("nick", nick)
				.getResultList();
		
		//nick is a unique field
		return users.isEmpty() ? null : users.get(0);
	}

	@Override
	public List<BlogUser> getAllBlogUsers() throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogUser as b").getResultList();
		
		return users;
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser author) throws DAOException {
		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = (List<BlogEntry>) JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogEntry as b where b.creator=:author").setParameter("author", author)
				.getResultList();
		return entries;
	}

}