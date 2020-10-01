package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * A runnable that performs Newton-Raphson iteration using the specified
 * polynomial. It fills the specified part of the given data array with the
 * results.
 * 
 * @author staver
 *
 */
public class CalculationWork implements Runnable {
	double reMin; // minimum real part of the complex plane.
	double reMax; // maximum real part of the complex plane.
	double imMin; // minimum imaginary part of the complex plane.
	double imMax; // maximum imaginary part of the complex plane.
	int width; // width of the GUI
	int height; // height of the GUI
	int yMin; // minimum y coordinate of the GUI
	int yMax; // maximum y coordinate of the GUI
	private ComplexRootedPolynomial polynomial; // polynomial used for Newton-Raphson iteration

	short[] data; // array with the indexes that get mapped to colors

	private double convergenceTreshold = 0.001;
	private int maxIter = 16 * 16 * 16; // maximum number of iterations for Newton-Raphson iteration
	private double rootTreshold = 0.002; // treshold for finding the closest root
	private int offset; // specifies where to start writing data in the given data array

	/**
	 * Constructs and initializes a new CalculationWork object with the specified
	 * values.
	 * 
	 * @param reMin      minimum real part of the complex plane.
	 * @param reMax      maximum real part of the complex plane.
	 * @param imMin      minimum imaginary part of the complex plane.
	 * @param imMax      maximum imaginary part of the complex plane.
	 * @param width      width of the GUI
	 * @param height     height of the GUI
	 * @param yMin       minimum y coordinate of the GUI
	 * @param yMax       maximum y coordinate of the GUI
	 * @param data       array of data to be filled
	 * @param polynomial polynomial used for Newton-Raphson iteration
	 * @param offset     specifies where to start writing data in the given data
	 *                   array
	 */
	public CalculationWork(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
			int yMax, short[] data, ComplexRootedPolynomial polynomial) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.data = data;
		this.polynomial = polynomial;
		this.offset = yMin * width;
	}

	/**
	 * Performs the Newton-Raphson iteration using the specified polynomial. It
	 * fills the data array with results starting from the position offset.<br>
	 * <br>
	 * {@inheritDoc}
	 */
	@Override
	public void run() {

		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				double re = (((double) x) / (width - 1)) * (reMax - reMin) + reMin;
				double im = ((double) (height - 1 - y) / (height - 1)) * (imMax - imMin) + imMin;

				Complex c = new Complex(re, im);

				Complex zn = c;
				int iter = 0;
				Complex znold;
				double module;
				do {
					Complex numerator = polynomial.apply(zn);
					Complex denominator = polynomial.toComplexPolynom().derive().apply(zn);
					znold = zn;
					Complex fraction = numerator.div(denominator);
					zn = zn.sub(fraction);
					module = znold.sub(zn).module();
					iter++;
				} while (module > convergenceTreshold && iter < maxIter);

				int index = polynomial.indexOfClosestRootFor(zn, rootTreshold);
				data[offset++] = (short) (index + 1);
			}
		}
	}

}
