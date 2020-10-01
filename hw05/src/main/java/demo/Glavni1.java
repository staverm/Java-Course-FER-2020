package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class that draws a Koch curve configured by methods.
 * 
 * @author Mauro Staver
 *
 */
public class Glavni1 {
	
	/**
	 * Main method that gets called when the program starts. It opens a GUI and
	 * generates an LSystem configured to draw a Koch curve and then draws it.
	 * 
	 * @param args command line arguments - not used here.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}
	
	/**
	 * The method returns an LSystem configured to draw a Koch curve.
	 * 
	 * @param provider LSystemBuilder object used for configuring the LSystem
	 * @return LSystem configured and generated by the LSystemBuilder
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {

		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4).setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0 / 3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}
}
