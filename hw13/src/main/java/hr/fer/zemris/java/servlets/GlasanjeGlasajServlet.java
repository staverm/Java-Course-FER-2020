package hr.fer.zemris.java.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that registers a vote by incrementing number of votes for that band
 * in appropriate text file("/WEB-INF/glasanje-rezultati.txt").
 * 
 * @author staver
 *
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Registers a vote for a band with id read from parameter 'id' by incrementing
	 * number of votes for that band in appropriate text
	 * file("/WEB-INF/glasanje-rezultati.txt")
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id").toString();

		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

		{
			File rez = new File(fileName);
			rez.createNewFile(); // create empty file if it doesn't exist
		}

		List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));

		int i;
		for (i = 0; i < fileContent.size(); i++) { // update appropriate number of votes
			String[] bandRating = fileContent.get(i).split("\\t");
			if (bandRating[0].equals(id)) {
				Integer rating = Integer.parseInt(bandRating[1]) + 1;
				fileContent.set(i, id + "\t" + rating);
				break;
			}
		}

		if (i == fileContent.size()) { // if band has no votes(it is not stored in this text file) add it to text file
										// with number of votes = 1
			fileContent.add(id + "\t1");
		}

		Files.write(Paths.get(fileName), fileContent, StandardCharsets.UTF_8);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
