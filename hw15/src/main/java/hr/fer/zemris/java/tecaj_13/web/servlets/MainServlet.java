package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.forms.BlogUserForm;

/**
 * Servlet that handles the home page of this web-app.
 * 
 * @author staver
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates and renders home page.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("current.user.id") == null) {
			BlogUser user = new BlogUser();
			BlogUserForm f = new BlogUserForm();
			f.fillFromUser(user);

			req.setAttribute("entry", f);
		}

		List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
		req.setAttribute("users", users);

		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

	/**
	 * Processes posted login form. Validates it and if everything is ok, creates a
	 * session for the now logged in user.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		BlogUserForm f = new BlogUserForm();
		f.fillFromHttpRequest(req);
		f.validateLogin();

		if (f.hasErrors()) {
			List<BlogUser> users = DAOProvider.getDAO().getAllBlogUsers();
			req.setAttribute("users", users);
			req.setAttribute("entry", f);
			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			return;
		}

		// successful login, set session attributes
		BlogUser user = DAOProvider.getDAO().getBlogUser(f.getNick());
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());

		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
