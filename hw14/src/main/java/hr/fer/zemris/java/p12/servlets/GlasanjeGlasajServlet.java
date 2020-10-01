package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hr.fer.zemris.java.p12.dao.DAOProvider;

/**
 * Servlet that registers a vote for some poll option by incrementing number of
 * votes for that option in persistence subsystem.
 * 
 * @author staver
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Registers a vote in some poll with id read from parameter 'pollID' for a poll
	 * option with id read from parameter 'optionID' by incrementing number of votes
	 * for that poll option in persistence subsystem.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String optionID = req.getParameter("optionID").toString();
		String pollID = req.getParameter("pollID").toString();

		DAOProvider.getDao().incrementVotesCount(Long.parseLong(optionID));

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}

}
