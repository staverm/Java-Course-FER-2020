package hr.fer.zemris.java.tecaj_13.web.servlets;

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
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Redirects client to '/servleti/main'.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

}
