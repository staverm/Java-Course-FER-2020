package hr.fer.zemris.java.p12.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;

/**
 * Servlet that generates and returns as response a pie-chart(png image) showing
 * poll voting results. <br>
 * <br>
 * If the request parameter 'pollID' is set (ie. not null) the voting results
 * for the poll with that id are fetched from the persistence subsystem. Otherwise the voting results are read from a session 
 * parameter 'options' (expected to be a list of options). <br>
 * This way the user has the option to provide the options list explicitly(by
 * session parameter 'options') or just provide a request parameter 'pollID' and
 * let the servlet to fetch the options list from persistence subsystem.
 * 
 * @author staver
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Fetches from persistence subsystem a list of options for a poll with id read
	 * from request parameter 'pollID'. If that parameter is null retrieves this
	 * list from session parameter 'options'. This list is then used to create a
	 * pie-chart which is sent in response as a png image.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		try {
			List<Poll.Option> options = null;

			if (req.getParameter("pollID") != null) {
				Poll poll = DAOProvider.getDao().getPollById(Long.parseLong(req.getParameter("pollID")));
				options = poll.getOptions();
			}else if (req.getSession().getAttribute("options") != null
					&& req.getSession().getAttribute("options") instanceof List) {
				options = (List<Poll.Option>) req.getSession().getAttribute("options"); // assume list of options
			} else {
				return;
			}

			JFreeChart chart = getChart(options);
			int width = 500;
			int height = 350;
			ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates and returns a pie-chart with voting results.
	 * 
	 * @param options list of options
	 * @return pie-chart with voting results.
	 */
	public JFreeChart getChart(List<Poll.Option> options) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		for (Poll.Option option : options) {
			if (option.getVotesCount() != 0) {
				dataset.setValue(option.getOptionTitle(), option.getVotesCount());
			}
		}

		boolean legend = true;
		boolean tooltips = true;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Rezultati", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.BLACK);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}

}
