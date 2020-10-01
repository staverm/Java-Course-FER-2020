package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class that models a bar chart.
 * 
 * @author staver
 *
 */
public class BarChart {

	/**
	 * List of XYValue objects to be displayed on the chart
	 */
	private List<XYValue> list;
	/**
	 * x-axis description.
	 */
	private String xDescription;
	/**
	 * y-axis description.
	 */
	private String yDescription;
	/**
	 * minimum y value on the y-axis.
	 */
	private int minY;
	/**
	 * maximum y value on the y-axis.
	 */
	private int maxY;
	/**
	 * Gap between y values on the y-axis.
	 */
	private int yGap;

	/**
	 * Constructs and initializes a new BarChart using the given data.
	 * 
	 * @param list         list of XYValue objects to be displayed on the chart
	 * @param xDescription x-axis description
	 * @param yDescription y-axis description
	 * @param minY         minimum y value on the y-axis
	 * @param maxY         maximum y value on the y-axis
	 * @param yGap         Gap between y values on the y-axis
	 */
	public BarChart(List<XYValue> list, String xDescription, String yDescription, int minY, int maxY, int yGap) {
		this.list = list;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.minY = minY;
		this.maxY = maxY;
		this.yGap = yGap;

		for (XYValue val : list) {
			if (val.getY() < minY) {
				throw new IllegalArgumentException("Y value: " + val.getY() + " is not >= yMin!");
			}
		}
	}

	/**
	 * Returns the list of XYValue objects to be displayed on the chart.
	 * 
	 * @return list of XYValue objects to be displayed on the chart
	 */
	public List<XYValue> getList() {
		return list;
	}

	/**
	 * Returns the x-axis description.
	 * 
	 * @return x-axis description
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Returns the y-axis description.
	 * 
	 * @return y-axis description
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Returns the minimum y value on the y-axis.
	 * 
	 * @return the minimum y value on the y-axis
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Returns the maximum y value on the y-axis.
	 * 
	 * @return the maximum y value on the y-axis
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Returns the gap between y values on the y-axis.
	 * 
	 * @return gap between y values on the y-axis
	 */
	public int getyGap() {
		return yGap;
	}
}
