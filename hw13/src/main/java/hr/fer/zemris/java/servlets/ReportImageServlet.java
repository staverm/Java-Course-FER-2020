package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Generates and send as response a pie chart as a png image. The pie chart
 * displays OS usage.
 * 
 * @author staver
 *
 */
public class ReportImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Generates and send as response a pie chart as a png image. The pie chart
	 * displays OS usage.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);
	}

	/**
	 * Generates and returns a pie-chart with appropriate data and styling.
	 * 
	 * @return pie-chart with appropriate data and styling.
	 */
	public JFreeChart getChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Android", 37.66);
		dataset.setValue("Windows", 35.94);
		dataset.setValue("iOS", 15.28);
		dataset.setValue("OS X", 8.59);
		dataset.setValue("Linux", 1.69);

		boolean legend = true;
		boolean tooltips = false;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.BLACK);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
