package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class that draws fractals configured by a text file.
 * 
 * @author Mauro Staver
 *
 */
public class Glavni3 {

	/**
	 * Main method that gets called when the program starts. It opens a GUI and
	 * generates an LSystem from the selected text file, which is used for drawing
	 * fractals.
	 * 
	 * @param args command line arguments - not used here.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
