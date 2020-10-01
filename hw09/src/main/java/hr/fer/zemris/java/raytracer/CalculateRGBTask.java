package hr.fer.zemris.java.raytracer;

import java.util.concurrent.RecursiveAction;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * A RecursiveAction task that fills the given red[], green[], and blue[] arrays
 * that hold RGB values for pixels. RGB values are calculated using ray tracing
 * and Phong's model for coloring. The task is parallelized because the
 * calculation for each pixel is independent from other pixels. The screen is
 * split by y coordinates into tracks, and tracks are then processed in
 * parallel.
 * 
 * @author staver
 *
 */
public class CalculateRGBTask extends RecursiveAction {

	private static final long serialVersionUID = 1L;
	private short[] red; // array for red values
	private short[] green; // array for green values
	private short[] blue; // array for blue values
	private Point3D xAxis; // x-axis of the coordinate system
	private Point3D yAxis; // y-axis of the coordinate system
	private Point3D screenCorner; // point representing the upper left corner of the screen(0,0)
	private int width; // screen width
	private int height; // screen height
	private int yMin; // minimum y coordinate of screen
	private int yMax; // maximum y coordinate of screen
	private double horizontal; // length of the rectangle that gets mapped to pixels
	private double vertical; // height of the rectangle that gets mapped to pixels
	private Point3D eye; // eye position point
	private Scene scene; // scene containing the objects and light sources

	private int treshold; // treshold for the number of y-s in a "track" of the screen
							// when a task gets a problem with the size < treshold it calculates it directly

	/**
	 * CalculateRGBTask constructor.
	 * 
	 * @param red          array for red values
	 * @param green        array for green values
	 * @param blue         array for blue values
	 * @param xAxis        x-axis of the coordinate system
	 * @param yAxis        y-axis of the coordinate system
	 * @param screenCorner point representing the upper left corner of the
	 *                     screen(0,0)
	 * @param width        screen width
	 * @param height       screen height
	 * @param yMin         minimum y coordinate of screen
	 * @param yMax         maximum y coordinate of screen
	 * @param horizontal   length of the rectangle that gets mapped to pixels
	 * @param vertical     height of the rectangle that gets mapped to pixels
	 * @param eye          eye position point
	 * @param scene        scene containing the objects and light sources
	 */
	public CalculateRGBTask(short[] red, short[] green, short[] blue, Point3D xAxis, Point3D yAxis,
			Point3D screenCorner, int width, int height, int yMin, int yMax, double horizontal, double vertical,
			Point3D eye, Scene scene) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.screenCorner = screenCorner;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.vertical = vertical;
		this.horizontal = horizontal;
		this.eye = eye;
		this.scene = scene;

		this.treshold = height / (8 * Runtime.getRuntime().availableProcessors());
	}

	/**
	 * Calculates the RGB values and fills the given red[], green[], and blue[]
	 * arrays for pixels whose y coordinate is in [yMin, yMax] using ray tracing and
	 * Phong's model for coloring. If yMax-yMin is too large, splits the task into 2
	 * new subtasks and invokes them.
	 */
	@Override
	protected void compute() {
		if (yMax - yMin + 1 <= treshold) {
			computeDirect();
			return;
		}
		invokeAll(
				new CalculateRGBTask(red, green, blue, xAxis, yAxis, screenCorner, width, height, yMin,
						yMin + (yMax - yMin) / 2, horizontal, vertical, eye, scene),
				new CalculateRGBTask(red, green, blue, xAxis, yAxis, screenCorner, width, height,
						yMin + (yMax - yMin) / 2 + 1, yMax, horizontal, vertical, eye, scene));

	}

	/**
	 * Calculates the RGB values and fills the given red[], green[], and blue[]
	 * arrays for pixels whose y coordinate is in [yMin, yMax] using ray tracing and
	 * Phong's model for coloring.
	 */
	public void computeDirect() {
		short[] rgb = new short[3];
		int offset = yMin * width;
		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				Point3D xAxisScaled = xAxis.scalarMultiply(horizontal * (((double) x) / (width - 1)));
				Point3D yAxisScaled = yAxis.scalarMultiply(vertical * ((double) y) / (height - 1));
				Point3D screenPoint = screenCorner.add(xAxisScaled).sub(yAxisScaled);

				Ray ray = Ray.fromPoints(eye, screenPoint);

				TracerUtil.tracer(scene, ray, rgb, eye);

				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

				offset++;
			}
		}

	}

}
