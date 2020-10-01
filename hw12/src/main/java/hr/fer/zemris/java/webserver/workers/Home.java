package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that generates an html page used as a home page that contains links to
 * other server resources.
 * 
 * @author staver
 *
 */
public class Home implements IWebWorker {

	/**
	 * Creates an html page used as a home page that contains links to other server
	 * resources. This page is then sent as an http response using the given
	 * context.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String background = context.getPersistentParameter("bgcolor");
		if (background == null) {
			context.setTemporaryParameter("background", "7F7F7F");
		} else {
			context.setTemporaryParameter("background", background);
		}

		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
