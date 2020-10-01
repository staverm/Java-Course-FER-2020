package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that demonstrates the use of ComplexPolynomial and
 * ComplexRootedPolynomial classes.
 * 
 * @author staver
 *
 */
public class ComplexPolynomialDemo {
	
	/**
	 * Main method that gets called when the program starts.
	 * 
	 * @param args command line arguments - not used here
	 */
	public static void main(String[] args) {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), Complex.ONE, Complex.ONE_NEG,
				Complex.IM, Complex.IM_NEG);
		ComplexPolynomial cp = crp.toComplexPolynom();
		System.out.println(crp);
		System.out.println(cp);
		System.out.println(cp.derive());
	}
}
