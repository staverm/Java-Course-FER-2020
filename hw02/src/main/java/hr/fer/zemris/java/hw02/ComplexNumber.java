package hr.fer.zemris.java.hw02;

/**
 * Class that represents complex numbers.
 * It implements methods for all basic operations with complex numbers.
 * @author Mauro Staver
 *
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;

	/**
	 * Constructor.
	 * Creates a new ComplexNumber object and initialized with the given values.
	 * @param real real part of the complex number
	 * @param imaginary imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Creates and returns a new ComplexNumber object initialized with the
	 *  given value for the real part of the complex number.
	 * @param real real part of the complex number
	 * @return new initialized ComplexNumber object
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Creates and returns a new ComplexNumber object initialized with the
	 *  given value for the imaginary part of the complex number.
	 * @param imaginary imaginary part of the complex number
	 * @return new initialized ComplexNumber object
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Creates and returns a new ComplexNumber object initialized with the given values.
	 * @param magnitude magnitude of the complex number in polar form.
	 * @param angle angle of the complex number in polar form.
	 * @return new initialized ComplexNumber object
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double real = magnitude*Math.cos(angle);
		double imaginary = magnitude*Math.sin(angle);
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Parses the string into a new ComplexNumber object and returns it.
	 * @param s string to parse into ComplexNumber
	 * @return ComplexNumber initialized with the given string
	 * @throws IllegalArgumentException if given string has an unsupported format.
	 */	
	public static ComplexNumber parse(String s) throws IllegalArgumentException{
		double re = 0;
		double im = 0;
		
		s = s.replaceAll("\\s+",""); //remove all whitespace
		
		try {
			//special case when size of string is 1
			if(s.length() == 1) {
				if(s.charAt(0) == 'i') {
					im = 1;
				}else {
					re = Double.parseDouble(s);
				}
				return new ComplexNumber(re,im);
			}
			
			for(int j = 0; j < 2; ++j) { //one pass for the real part and one for the imaginary

				int k; // end index
				for (k = 1; k < s.length(); ++k) {
					if (!Character.isDigit(s.charAt(k)) && s.charAt(k) != '.') {
						break;
					}
				}
				// k is now the index of the first nonDigit char that is not a '.'
				// of a substring of s starting at index 1

				if (k == s.length()) { // end of the string reached, add real part and break
					re = Double.parseDouble(s.substring(0, k));
					break;
					
				}else if (s.charAt(k) == 'i') { // search ended at char 'i', add imaginary part and break
					
					if (s.length() == 2) { //special case when size of string is 2
						if (s.charAt(0) == '+') { //in case "+i"
							im = 1.0;
						} else if (s.charAt(0) == '-') { //in case "-i"
							im = -1.0;
						} else { // try to parse, s has format for example "2i"
							im = Double.parseDouble(s.substring(0, s.length() - 1)); 
						}

					} else { //size > 2, try to parse
						im = Double.parseDouble(s.substring(0, s.length() - 1));
					}

					break;

				} else {//search ended at the end of the real part of the string,
						//add real part and repeat for imaginary part
					re = Double.parseDouble(s.substring(0, k));
					s = s.substring(k); // get imaginary part of the string, back into for loop if on first pass
				}
			}//end of for outer for loop
			
		} catch (NumberFormatException ex) { // catch any exception thrown by any parseDouble
			throw new IllegalArgumentException("Bad string format.");
		}

		return new ComplexNumber(re, im);
	}
	
	/**
	 * Returns the real part of the ComplexNumber.
	 * @return double - real part of the ComplexNumber.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns the imaginary part of the ComplexNumber.
	 * @return double - imaginary part of the ComplexNumber.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Returns the magnitude of the ComplexNumber in polar form.
	 * @return double - magnitude of the ComplexNumber in polar form.
	 */
	public double getMagnitude() {
		return Math.sqrt(real*real+imaginary*imaginary);
	}
	
	/**
	 * Returns the angle in radians of the ComplexNumber in polar form.
	 * @return double - angle in radians in range [0,2PI] of the ComplexNumber in polar form.
	 */
	public double getAngle() {
		double angle = Math.atan2(imaginary, real);
		if(angle < 0) angle += 2*Math.PI;
		return angle;
	}
	
	/**
	 * Adds the given ComplexNumber object to this object and returns the resulting ComplexNumber.
	 * @param c ComplexNumber to be added.
	 * @return the resulting ComplexNumber.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real+c.getReal(), imaginary+c.getImaginary());
	}
	
	/**
	 * Subtracts the given ComplexNumber object from this object and returns the resulting ComplexNumber.
	 * @param c ComplexNumber to be subtracted.
	 * @return the resulting ComplexNumber.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real-c.getReal(), imaginary-c.getImaginary());
	}
	
	/**
	 * Multiplies the given ComplexNumber object with this object and returns the resulting ComplexNumber.
	 * @param c ComplexNumber to be multiplied.
	 * @return the resulting ComplexNumber.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real*c.getReal() - imaginary*c.getImaginary(),
				real*c.getImaginary() + imaginary*c.getReal());
	}
	
	/**
	 * Divides the given ComplexNumber object with this object and returns the resulting ComplexNumber.
	 * The given ComplexNumber is used as a divisor, and this object as a dividend.
	 * @param c ComplexNumber to be multiplied.
	 * @return the resulting ComplexNumber.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double sumOfSquares = c.getReal()*c.getReal() + c.getImaginary()*c.getImaginary();
		double re = c.getReal()/sumOfSquares;
		double im = -c.getImaginary()/sumOfSquares;
		
		return mul(new ComplexNumber(re, im)); //multiply by 1/c
	}
	
	/**
	 * Calculates and returns this ComplexNumber raised to the given power.
	 * @param n power
	 * @return this ComplexNumber raised to the given power.
	 * @throws IllegalArgumentException if given power is less than 0
	 */
	public ComplexNumber power(int n) throws IllegalArgumentException{
		if(n < 0) {
			throw new IllegalArgumentException("Argument has to be >= 0");
		}
		double rPowN = Math.pow(this.getMagnitude(), n);
		double re = rPowN * Math.cos(n*this.getAngle());
		double im = rPowN * Math.sin(n*this.getAngle());
		
		return new ComplexNumber(re, im); //DeMoivre's Theorem z^n = r^n(cos(nθ), isin(nθ))
	}
	
	/**
	 * Calculates and returns the n-th roots of this ComplexNumber.
	 * @param n root value
	 * @return Array of ComplexNumbers which contains the n-th roots of this ComplexNumber.
	 * @throws IllegalArgumentException if root value is <= 0
	 */
	public ComplexNumber[] root(int n) throws IllegalArgumentException{
		if(n <= 0) {
			throw new IllegalArgumentException("Argument has to be > 0");
		}
		ComplexNumber[] roots = new ComplexNumber[n];
		double rPowOneOverN = Math.pow(this.getMagnitude(), 1.0/n); //r^(1/n)
		
		for(int k = 0; k < n; ++k) {
			double re = rPowOneOverN * Math.cos((this.getAngle()+2*k*Math.PI)/n);
			double im = rPowOneOverN * Math.sin((this.getAngle()+2*k*Math.PI)/n);
			
			roots[k] = new ComplexNumber(re, im); //DeMoivre's Theorem 
		}
		return roots;
	}
	
	/**
	 * Returns this ComplexNumber in String format.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();	
		
		if(real == 0 && imaginary == 0) {
			sb.append(0);
		}else {
			if(real != 0) {
				sb.append(Double.toString(real));
				
				if(imaginary > 0) {
					sb.append("+");
				}		
			}
		
			if(imaginary != 0) {
				sb.append(Double.toString(imaginary) + "i");
			}
		}
		
		return sb.toString();
	}
}
