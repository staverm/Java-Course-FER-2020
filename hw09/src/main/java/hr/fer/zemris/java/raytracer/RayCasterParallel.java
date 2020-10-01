package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that opens a GUI and renders a scene using ray tracing and Phong's
 * model for coloring. This program uses parallelization to speed up rendering.
 * 
 * @author staver
 *
 */
public class RayCasterParallel {

	/**
	 * Main method that gets called when the program starts. It opens a GUI and
	 * renders a scene using ray tracing and Phong's model for coloring.
	 * 
	 * @param args command line arguments - not used here
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Creates and returns a new IRayTracerProducer. Calling <code>produce()</code>
	 * method on this object calculates the RGB values of each pixel using ray
	 * tracing and Phong's model for coloring, then the
	 * GUI(IRayTracerResultObserver) is updated with the calculated values. The
	 * pixel coloring calculations are parallelized.
	 * 
	 * @return IRayTracerProducer object - calling <code>produce()</code> method on
	 *         this object renders a scene on the GUI
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			
			/**
			 * Calculates the RGB values of each pixel using ray tracing and Phong's model
			 * for coloring, then the GUI(IRayTracerResultObserver) is called with the
			 * calculated values. The calculations are parallelized.
			 */
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D OG = view.sub(eye).normalize(); // vector from eye to view normalized
				Point3D VUV = viewUp.normalize();

				Point3D yAxis = VUV.sub(OG.scalarMultiply(OG.scalarProduct(VUV))).normalize();
				Point3D xAxis = OG.vectorProduct(yAxis).normalize();
				// Point3D zAxis = xAxis.vectorProduct(yAxis).normalize().negate();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculateRGBTask(red, green, blue, xAxis, yAxis, screenCorner, width, height, 0, height - 1,
						horizontal, vertical, eye, scene));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");

			}
		};
	}

}
