package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that checks if there is a background color parameter "bgcolor": if yes
 * creates a new permanent parameter for background color using the previously
 * obtained value. Creates and sends as response an html page that states
 * whether the operation was successful.
 * 
 * @author staver
 *
 */
public class BgColorWorker implements IWebWorker {

	/**
	 * Checks if there is a background color parameter "bgcolor": if yes creates a
	 * new permanent parameter for background color using the previously obtained
	 * value. Creates and sends as response an html page that states whether the
	 * operation was successful.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getParameter("bgcolor");
		boolean updated = false;
		if (bgcolor != null && bgcolor.length() == 6) {
			context.setPersistentParameter("bgcolor", bgcolor);
			updated = true;
		}

		context.write("<html><head></head><body><h2>Color is" + (updated == true ? " " : " not ") + "updated!</h2>");
		context.write("<a href=\"/index2.html\">Home</a>");
		context.write("</body></html>");

	}

}
