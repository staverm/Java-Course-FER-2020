package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that renders the home page for this web-app.
 * 
 * @author staver
 *
 */
@WebServlet("/index.html")
public class indexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Redirects client to '/servleti/index.html'.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect("servleti/index.html");
	}

}
