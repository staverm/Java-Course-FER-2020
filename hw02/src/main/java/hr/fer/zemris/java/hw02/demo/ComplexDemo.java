package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class that demonstrates the behavior and use of class ComplexNumber.
 * @author Mauro Staver
 *
 */
public class ComplexDemo {

	/**
	 * Main method that gets called when the program starts.
	 * The method demonstrates the behavior and use of class ComplexNumber.
	 * @param args Command line arguments, not used in this program.
	 */
	public static void main(String[] args) {
		
		ComplexNumber c1 = new ComplexNumber(2,3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
		
	}
}
