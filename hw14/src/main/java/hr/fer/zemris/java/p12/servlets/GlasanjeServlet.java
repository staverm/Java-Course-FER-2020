package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Servlet that generates an html page for voting for a poll with id read from
 * 'pollID' request parameter.
 * 
 * @author staver
 *
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Fetches from persistence subsystem a Poll object with poll id read from
	 * 'pollID' request parameter. Forwards this object to a jsp that does actual
	 * html generation.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Long pollID = Long.parseLong(req.getParameter("pollID").toString());
		Poll poll = DAOProvider.getDao().getPollById(pollID);

		req.setAttribute("poll", poll);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
