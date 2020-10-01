package hr.fer.zemris.java.webserver;

/**
 * Interface that models a worker that serves clients. Warning: workers should
 * not use class properties without explicit synchronization.
 * 
 * @author staver
 *
 */
public interface IWebWorker {

	/**
	 * Serves client using the given context.
	 * 
	 * @param context context to be used for serving the client
	 * @throws Exception if exceptions occur while serving
	 */
	public void processRequest(RequestContext context) throws Exception;

}
