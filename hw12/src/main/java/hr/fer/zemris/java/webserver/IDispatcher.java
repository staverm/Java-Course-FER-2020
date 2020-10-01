package hr.fer.zemris.java.webserver;

/**
 * Models a dispatcher that serves resources to client.
 * 
 * @author staver
 *
 */
public interface IDispatcher {
	/**
	 * Serves the resource located at given path(relative to documentRoot) to the
	 * client.
	 * 
	 * @param urlPath path(relative to documentRoot) of the resource to be served
	 * @throws Exception if exceptions occur while serving
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
