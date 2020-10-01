package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;
import javax.swing.JComponent;

/**
 * Class that implements a component(draws it) based on the given BarChart.
 * 
 * @author staver
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	/**
	 * Chart used to draw this component.
	 */
	private BarChart chart;
	/**
	 * Gap between end of axis and end of screen.
	 */
	private int gap = 100;
	/**
	 * Gap between the starting point of both axes and the left side end of the screen.
	 * (the x coordinate of starting point for both axes)
	 */
	private int xGap;
	/**
	 * Length of the x-axis.
	 */
	private int xAxisLength;
	/**
	 * Length of the y-axis.
	 */
	private int yAxisLength;
	/**
	 * X-axis unit length.
	 */
	private double xUnit;
	/**
	 * Y-axis unit length.
	 */
	private double yUnit;
	
	/**
	 * Component height
	 */
	private int height;
	
	/**
	 * Component width
	 */
	private int width;

	/**
	 * Constructs and initializes a new BarChartComponent based on the given chart.
	 * 
	 * @param chart BarChart used to initialize this BarChartComponent
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	/**
	 * Paints this component using the given Graphics object. Draws the x-axis and
	 * y-axis with numbers and their description, and polygons for each XYValue
	 * object in the BarChart list.
	 * 
	 * @param g Graphics object to be used for drawing
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fontMetrics = g2d.getFontMetrics();

		height = this.getHeight();
		width = this.getWidth();

		xGap = fontMetrics.stringWidth(Integer.toString(chart.getMaxY())) + 90;
		xAxisLength = width - gap - xGap;
		yAxisLength = height - 2 * gap;
		yUnit = (double) yAxisLength / (chart.getMaxY() - chart.getMinY());
		xUnit = (double) xAxisLength / chart.getList().size();		

		AffineTransform defaultAt = g2d.getTransform();
		g2d.rotate(-Math.PI / 2);
		g2d.drawString(chart.getyDescription(),
				-yAxisLength / 2 - gap - fontMetrics.stringWidth(chart.getyDescription()) / 2, 50);

		g2d.setTransform(defaultAt);

		g2d.drawString(chart.getxDescription(),
				xGap + xAxisLength / 2 - fontMetrics.stringWidth(chart.getxDescription()) / 2,
				height - gap / 2);

		g.setColor(Color.decode("#A9A9A9"));
		drawPolygons(g);

		g.setColor(Color.black);
		drawYAxisNumbers(g);
		drawXAxisNumbers(g);
		drawAxis(g, xGap, height - gap, xGap, gap); // y-axis
		drawAxis(g, xGap, height - gap, width - gap, height - gap); // x-axis
	}

	/**
	 * Draws a polygon using the given Graphics object for each XYValue in the
	 * BarChart list.
	 * 
	 * @param g Graphics object to be used for drawing
	 */
	private void drawPolygons(Graphics g) {
		List<XYValue> list = chart.getList();

		int n = list.size();

		double x = xGap;
		int y = height - gap;

		for (int i = 0; i < n; i++) {
			g.fillPolygon(
					new int[] { (int) Math.round(x + 2), (int) Math.round(x + 2),
							(int) Math.round(x + (double) xAxisLength / n - 2),
							(int) Math.round(x + (double) xAxisLength / n - 2) },
					new int[] { y, (int) Math.round(y - list.get(i).getY() * yUnit),
							(int) Math.round(y - list.get(i).getY() * yUnit), y },
					4);

			x += xUnit;
		}

	}

	/**
	 * Draws numbers and tick marks on the y-axis using the given Graphics object.
	 * 
	 * @param g Graphics object to be used for drawing
	 */
	private void drawYAxisNumbers(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		FontMetrics fontMetrics = g2d.getFontMetrics();
		int endPoint = xGap - 10;

		double y = height - gap;
		int tickLength = 4;
		for (int i = chart.getMinY(); i <= chart.getMaxY(); i += chart.getyGap()) {
			String num = Integer.toString(i);

			g.drawString(num, endPoint - fontMetrics.stringWidth(num), (int) Math.round(y + 4)); // +4 to center string
																									// with ticks
			g.drawLine(xGap - tickLength, (int) Math.round(y), xGap + tickLength, (int) Math.round(y));

			y -= yUnit * chart.getyGap();
		}
	}

	/**
	 * Draws numbers and tick marks on the x-axis using the given Graphics object.
	 * 
	 * @param g Graphics object to be used for drawing
	 */
	private void drawXAxisNumbers(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		FontMetrics fontMetrics = g2d.getFontMetrics();

		int n = chart.getList().size();

		double x = xGap + xUnit / 2;
		int y = height - gap + 20;

		double xLine = xGap;
		int tickLength = 4;
		for (int i = 0; i < n; i++) {
			String num = Integer.toString(chart.getList().get(i).getX());
			g.drawString(num, (int) Math.round(x - ((double) fontMetrics.stringWidth(num)) / 2), y);
			x += xUnit;

			g.drawLine((int) Math.round(xLine), height - gap + tickLength, (int) Math.round(xLine),
					height - gap - tickLength);
			xLine += xUnit;
		}
	}

	/**
	 * Draws an axis from the starting point (x1,y1) to (x2,y2) using the given
	 * Graphics object.
	 * 
	 * @param g  Graphics object to be used for drawing
	 * @param x1 x coordinate of the starting point
	 * @param y1 y coordinate of the starting point
	 * @param x2 x coordinate of the ending point
	 * @param y2 y coordinate of the ending point
	 */
	private void drawAxis(Graphics g, int x1, int y1, int x2, int y2) {
		Graphics2D g2d = (Graphics2D) g.create();
		int dx = x2 - x1;
		int dy = y2 - y1;
		double angle = Math.atan2(dy, dx);
		int len = (int) Math.sqrt(dx * dx + dy * dy);

		AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
		at.concatenate(AffineTransform.getRotateInstance(angle));
		g2d.transform(at);

		len += 5; // draw axis slightly longer
		g2d.drawLine(0, 0, len, 0);
		
		int arrowSize = 4;

		g2d.fillPolygon(new int[] { len, len, len + 2 * arrowSize }, new int[] { arrowSize, -arrowSize, 0 }, 3);
	}

}
