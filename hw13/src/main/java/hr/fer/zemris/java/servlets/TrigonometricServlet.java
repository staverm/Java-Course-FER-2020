package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that generates an html page with a table of sin and cos values for
 * given range of integers [a,b] where a and b are given as parameters 'a' and 'b'.
 * 
 * @author staver
 *
 */
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Sets appropriate parameters and delegates html generation to a jsp.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a, b;
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch (NumberFormatException ex) {
			a = 0;
		}
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch (NumberFormatException ex) {
			b = 360;
		}

		if (a > b) { // swap
			Integer tmp = a;
			a = b;
			b = tmp;
		}

		if (b > a + 720) {
			b = a + 720;
		}

		req.setAttribute("a", a);
		req.setAttribute("b", b);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}
