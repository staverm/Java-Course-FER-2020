package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Servlet that renders a page with all defined polls and links that direct to a
 * voting page for each poll. The rendered page is used as a home page
 * for this web-app.
 * 
 * @author staver
 *
 */
@WebServlet("/servleti/index.html")
public class PollsListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Fetches defined polls from persistence subsystem and passes them to a jsp
	 * that renders the actual html page.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		List<Poll> polls = DAOProvider.getDao().getDefinedPolls();
		req.setAttribute("polls", polls);

		req.getRequestDispatcher("/WEB-INF/pages/pollsList.jsp").forward(req, resp);
	}

}
