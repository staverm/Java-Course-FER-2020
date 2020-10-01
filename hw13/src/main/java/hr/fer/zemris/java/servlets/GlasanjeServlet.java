package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that dynamically generates an html page with links to vote for bands
 * which are read from a text file.
 * 
 * @author staver
 *
 */
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Reads bands from text file into a list and forwards it to a jsp that does the
	 * actual html generation.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		try {
			List<Band> bands = Band.parseBands(fileName);
			req.setAttribute("bands", bands);
		} catch (Exception e) {
			e.printStackTrace();
		}

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
