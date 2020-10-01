package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that generates an html page that displays given context's parameters
 * in a table.
 * 
 * @author staver
 *
 */
public class EchoParams implements IWebWorker {

	/**
	 * Generates an html page that displays (name,value) pairs of given context's
	 * paremeters in a table. This html page is sent as an http response.
	 */
	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");

		context.write("<html><body>");
		context.write("<h2>Parameters</h2>");
		context.write("<table><tr><th>Name</th><th>Value</th></tr>");
		for (String key : context.getParameterNames()) {
			context.write("<tr><td>" + key + "</td><td>" + context.getParameter(key) + "</td></tr>");
		}
		context.write("</table>");

		context.write("</body></html>");

	}

}
