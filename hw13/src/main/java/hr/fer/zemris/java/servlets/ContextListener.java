package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * ServletContextListener that remembers time of context initialization (as
 * epoch time in milliseconds).
 * 
 * @author staver
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {
	private ServletContext context = null;

	/**
	 * Sets context attribute "CreationTime" to current epoch time in milliseconds.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();
		context.setAttribute("CreationTime", System.currentTimeMillis());
	}

	/**
	 * Removes context attribute "CreationTime".
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		context.removeAttribute("CreationTime");
	}

}
