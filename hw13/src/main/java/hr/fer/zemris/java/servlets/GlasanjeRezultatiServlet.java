package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that generates an html page with band voting results.
 * 
 * @author staver
 *
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Reads bands from text file and reads voting results from another text file.
	 * From this information, generates a list of bands where each band has it's
	 * votes value set to appropriate value. Forwards this list as well as a list of
	 * winners to a jsp that does actual html generation.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		try {
			List<Band> bands = Band.getResults(bandsFileName, resultsFileName);
			req.getSession().setAttribute("bands", bands);
			
			List<Band> winners = new ArrayList<>();
			winners.add(bands.get(0));
			for (int i = 1; i < bands.size(); i++) {
				if (bands.get(i).getVotes() == bands.get(0).getVotes()) {
					winners.add(bands.get(i));
				}
			}

			req.setAttribute("winners", winners);
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
