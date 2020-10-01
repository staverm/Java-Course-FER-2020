package hr.fer.zemris.java.p12;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * ServletContextListener which handles connection pool creation/destruction and
 * database initialization(creates initial tables, inserts initial rows).
 * 
 * @author staver
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Set's up connection pool to database specified with
	 * /WEB-INF/dbsettings.properties file. Initializes 2 tables(Polls and
	 * PollOptions) and appropriate initial rows.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Properties properties = new Properties();
			properties.load(new FileReader(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")));

			String connectionURL = "jdbc:derby://" + properties.getProperty("host") + ":"
					+ properties.getProperty("port") + "/" + properties.getProperty("name");

			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");

			cpds.setJdbcUrl(connectionURL);
			cpds.setUser(properties.getProperty("user"));
			cpds.setPassword(properties.getProperty("password"));

			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

			// initialize tables
			Connection con = null;
			try {
				con = cpds.getConnection();
			} catch (SQLException e) {
				throw new IOException("Baza podataka nije dostupna.", e);
			}

			SQLConnectionProvider.setConnection(con);

			try {
				DAOProvider.getDao().initializeTablesIfNotInitialized();
			} finally {
				SQLConnectionProvider.setConnection(null);
				try {
					con.close();
				} catch (SQLException ignorable) {
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Unable to initialize connection pool or initial tables.", e);
		}
	}

	/**
	 * Destroys connection pool.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
