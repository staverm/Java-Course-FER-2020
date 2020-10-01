package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that sets background color for all jsps of this webapp. The color is
 * stored as a session attribute so it's only valid for that session.
 * 
 * @author staver
 *
 */
public class SetColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Sets session attribute 'pickedBgCol' to value of request's parameter 'color'
	 * and redirects to /index.jsp
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String color = req.getParameter("color");
		req.getSession().setAttribute("pickedBgCol", color);

		resp.sendRedirect(req.getContextPath() + "/index.jsp");
	}
}
