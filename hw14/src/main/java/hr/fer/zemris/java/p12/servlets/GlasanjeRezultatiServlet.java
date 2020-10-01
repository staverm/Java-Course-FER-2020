package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Servlet that generates an html page with poll voting results.
 * 
 * @author staver
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Reads list of poll options from persistence subsystem for a poll with id read from
	 * request parameter 'pollID'. Forwards this list as well as a list of winners
	 * to a jsp that does actual html generation.
	 */
	@Override

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Poll poll = DAOProvider.getDao().getPollById(Long.parseLong(req.getParameter("pollID")));

			List<Poll.Option> options = poll.getOptions();

			// find winners(another solution is to add appropriate method in DAO for
			// fetching winners straight from database)
			Collections.sort(options, (o1, o2) -> o2.getVotesCount().compareTo(o1.getVotesCount()));
			List<Poll.Option> winners = new ArrayList<>();
			winners.add(options.get(0));
			for (int i = 1; i < options.size(); i++) {
				if (options.get(i).getVotesCount() == options.get(0).getVotesCount()) {
					winners.add(options.get(i));
				}
			}

			req.getSession().setAttribute("options", options);
			req.setAttribute("winners", winners);
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
