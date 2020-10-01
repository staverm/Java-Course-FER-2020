package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Program that prompts user to enter complex numbers - roots that form a
 * polynomial. That polynomial is used to draw a fractal based on Newton-Raphson
 * iteration.
 * 
 * @author staver
 *
 */
public class Newton {

	/**
	 * Main method that gets called when the program starts. It prompts user to
	 * enter complex numbers - roots that form a polynomial. That polynomial is used
	 * to draw a fractal based on Newton-Raphson iteration.
	 * 
	 * @param args command line arguments - not used here
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		int index = 1;
		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new ArrayList<>();
		while (true) {
			System.out.print("Root " + index + "> ");
			String line = sc.nextLine();
			if (line.equals("done")) {
				break;
			}

			try {
				Complex root = parseToComplex(line);
				roots.add(root);
				index++;
			} catch (IllegalArgumentException ex) {
				System.out.println("Bad format. Please try again.");
			}
		}

		sc.close();
		
		if(roots.size() < 2) {
			System.out.println("Less than two roots entered.");
			System.exit(0);
		}

		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(
				new FractalProducer(new ComplexRootedPolynomial(Complex.ONE, roots.stream().toArray(Complex[]::new))));
	}

	/**
	 * Parses the string into a new complex number and returns it.
	 * 
	 * @param s string to parse into ComplexNumber
	 * @return the parsed complex number object (instance of class Complex)
	 * @throws IllegalArgumentException if given string has an unsupported format.
	 */
	private static Complex parseToComplex(String s) {
		double re = 0;
		double im = 0;

		s = s.replaceAll("\\s+", ""); // remove all whitespace

		try {
			// special case when size of string is 1
			if (s.length() == 1) {
				if (s.charAt(0) == 'i') {
					im = 1;
				} else {
					re = Double.parseDouble(s);
				}
				return new Complex(re, im);
			}

			// string size > 1
			int indexOfi = s.indexOf('i');
			if (indexOfi == -1) { // no imaginary part
				re = Double.parseDouble(s);
				return new Complex(re, im);
			}

			if (indexOfi > 1) { // real part exists
				re = Double.parseDouble(s.substring(0, indexOfi - 1));
			}
			if (indexOfi == 0) { // if for example "i3" turn it to "+i3"
				s = "+" + s;
				im = parseImaginary(s);
			} else {
				im = parseImaginary(s.substring(indexOfi - 1));
			}

		} catch (NumberFormatException ex) { // catch any exception thrown by any parseDouble
			throw new IllegalArgumentException("Bad string format.");
		}

		return new Complex(re, im);
	}

	/**
	 * Extracts and returns the imaginary part of the given string. The string is
	 * expected to have no real part and to be of format "+ib" or "-ib" where b are
	 * some digits or nothing.
	 * 
	 * @param s String to be parsed
	 * @return double containing the imaginary part
	 */
	private static double parseImaginary(String s) { // parses strings of format "-i300" or "+i200"
		double im = 0.0;
		if (s.length() == 2) {
			if (s.charAt(0) == '+') { // in case "+i"
				im = 1.0;
			} else if (s.charAt(0) == '-') { // in case "-i"
				im = -1.0;
			}
		} else {
			im = Double.parseDouble(s.substring(2));
			if (s.charAt(0) == '-') {
				im = -im + 0.0; // adding 0.0 turns -0.0 into 0.0
			}
		}
		return im;
	}

}
