package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that generates a simple html page that displays the sum of two passed
 * parameters and an image.
 * 
 * @author staver
 *
 */
public class SumWorker implements IWebWorker {

	/**
	 * Calculates the sum of passed parameters 'a' and 'b'. If parameters don't
	 * exist or can not be parsed to integers, value 1 is used. Places temporary
	 * parameters 'varA', 'varB', 'zbroj', 'imgName' in given request context and
	 * delegates html generation to "/private/pages/calc.smscr". ('zbroj' is the sum
	 * of 'varA' and 'varB', 'imgName' is image name to be displayed)
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String a = context.getParameter("a");
		String b = context.getParameter("b");

		int varA, varB;
		try {
			varA = Integer.parseInt(a);
		} catch (NumberFormatException ex) {
			varA = 1;
		}

		try {
			varB = Integer.parseInt(b);
		} catch (NumberFormatException ex) {
			varB = 1;
		}

		context.setTemporaryParameter("varA", Integer.toString(varA));
		context.setTemporaryParameter("varB", Integer.toString(varB));
		int zbroj = varA + varB;
		context.setTemporaryParameter("zbroj", Integer.toString(zbroj));
		context.setTemporaryParameter("imgName", zbroj % 2 == 0 ? "spyro.png" : "pokemon.png");

		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
