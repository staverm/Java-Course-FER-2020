package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents an HTTP server response. Offers methods for setting up
 * response context and for writing the response to output stream connected with
 * the client.
 * 
 * @author staver
 *
 */
public class RequestContext {
	/**
	 * Class that represents a HTTP request cookie.
	 * 
	 * @author staver
	 *
	 */
	public static class RCCookie {
		private String name; // cookie name
		private String value; // cookie value
		private String domain; // domain property
		private String path; // path propery
		private Integer maxAge; // max age cookie property(time to live for this cookie)
		private boolean httpOnly; // httpOnly property

		/**
		 * RCCookie constructor. Creates a new RCCookie object and initializes it to
		 * given values.
		 * 
		 * @param name     cookie name
		 * @param value    cookie value
		 * @param maxAge   max age cookie property (time to live for this cookie)
		 * @param domain   cookie domain property
		 * @param path     cookie path property
		 * @param httpOnly httpOnly property
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}

		/**
		 * Returns this cookie's name.
		 * 
		 * @return cookie's name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns this cookie's value.
		 * 
		 * @return cookie's value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns this cookie's domain.
		 * 
		 * @return cookie's domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns this cookie's path.
		 * 
		 * @return cookie's path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns this cookie's max age property.
		 * 
		 * @return cookie's max age property
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Returns true if this cookie's httpOnly property is set to true, false
		 * otherwise.
		 * 
		 * @return true if this cookie's httpOnly property is set to true, false
		 *         otherwise
		 */
		public boolean isHttpOnly() {
			return httpOnly;
		}
	}

	private OutputStream outputStream; // output stream to write
	private Charset charset; // charset to be used to write

	private String encoding = "UTF-8"; // encoding to be used to write
	private int statusCode = 200;
	private String statusText = "OK";
	private String mimeType = "text/html";
	private Long contentLength = null;

	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters = new HashMap<>();
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies; // list of cookies to be sent

	private IDispatcher dispatcher;
	private String sessionID;
	private boolean headerGenerated = false; // indicates if header is generated and written to output stream

	/**
	 * RequestContext constructor. Creates a new RequestContext object and
	 * initializes it to the given values.
	 * 
	 * @param outputStream         output stream to be set, can't be null - header +
	 *                             body of the HTTP response will be written here
	 * @param parameters           parameters map to be set, if null assigns empty
	 *                             map
	 * @param persistentParameters persistent parameters map to be set, if null
	 *                             assigns empty map
	 * @param outputCookies        output cookies list to be set, if null assigns
	 *                             empty map
	 * @throws IllegalArgumentException if given outputStream argument is null
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		if (outputStream == null) {
			throw new IllegalArgumentException("outputStream can not be null!");
		}

		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}

	/**
	 * RequestContext constructor. Creates a new RequestContext object and
	 * initializes it to the given values.
	 * 
	 * @param outputStream         output stream to be set, can't be null - header +
	 *                             body of the HTTP response will be written here
	 * @param parameters           parameters map to be set, if null assigns empty
	 *                             map
	 * @param persistentParameters persistent parameters map to be set, if null
	 *                             assigns empty map
	 * @param outputCookies        output cookies list to be set, if null assigns
	 *                             empty map
	 * @param dispatcher           dispatcher for serving resources
	 * @param sessionID            session ID connected with this response
	 * @throws IllegalArgumentException if given outputStream argument is null
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sessionID) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.sessionID = sessionID;
	}

	/**
	 * Returns dispatcher.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Sets this request's encoding to the given argument.
	 * 
	 * @param encoding encoding to be set
	 * @throws RuntimeException if header is already generated
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.encoding = encoding;
	}

	/**
	 * Sets this request's status code to the given argument.
	 * 
	 * @param statusCode status code to be set
	 * @throws RuntimeException if header is already generated
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets this request's status text to the given argument.
	 * 
	 * @param statusText status text to be set
	 * @throws RuntimeException if header is already generated
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusText = statusText;
	}

	/**
	 * Sets this request's mime type to the given argument.
	 * 
	 * @param mimeType mime type to be set
	 * @throws RuntimeException if header is already generated
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.mimeType = mimeType;
	}

	/**
	 * Sets this request's content length to the given argument.
	 * 
	 * @param contentLength content length to be set
	 * @throws RuntimeException if header is already generated
	 */
	public void setContentLength(long contentLength) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.contentLength = contentLength;
	}

	/**
	 * Adds the given cookie into a list of this request's output cookies.
	 * 
	 * @param cookie cookie to be added
	 * @throws RuntimeException if header is already generated
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		outputCookies.add(cookie);
	}

	/**
	 * Returns this request's parameters map.
	 * 
	 * @return parameters map
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Returns this request's temporary parameters map.
	 * 
	 * @return temporary parameters map
	 */
	public Map<String, String> getTemporaryParameters() {
		return temporaryParameters;
	}

	/**
	 * Sets this request's temporary parameters map to the given map. If null is
	 * given, new empty map is created and assigned.
	 * 
	 * @param temporaryParameters temporary parameters map to be set
	 */
	public void setTemporaryParameters(Map<String, String> temporaryParameters) {
		this.temporaryParameters = temporaryParameters;
	}

	/**
	 * Returns this request's persistent parameters map.
	 * 
	 * @return persistent parameters map
	 */
	public Map<String, String> getPersistentParameters() {
		return persistentParameters;
	}

	/**
	 * Sets this request's persistent parameters map to the given map. If null is
	 * given, new empty map is created and assigned.
	 * 
	 * @param persistentParameters persistent parameters map to be set
	 */
	public void setPersistentParameters(Map<String, String> persistentParameters) {
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
	}

	/**
	 * Returns value from parameters map, or null if none exist.
	 * 
	 * @param name key of the associated parameter
	 * @return value from parameters map, or null if none exist
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Returns names of all parameters in parameters map as an unmodifiable set.
	 * 
	 * @return names of all parameters in parameters map as an unmodifiable set
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns value from persistentParameters map (or null if no association
	 * exists).
	 * 
	 * @param name key of the associated persistent parameter
	 * @return value from persistentParameters map (or null if no association
	 *         exists)
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Returns names of all parameters in persistent parameters map as an
	 * unmodifiable set.
	 * 
	 * @return names of all parameters in persistent parameters map as an
	 *         unmodifiable set
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Stores the given value to persistent parameters map and associates the given
	 * name string with it.
	 * 
	 * @param name  key to be associated with the given persistent parameter value
	 * @param value value to be stored in persistent parameters map
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes a value associated with the given key from persistent parameters map
	 * 
	 * @param name key of the associated persistent parameter
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Returns value from temporary parameters map (or null if no association
	 * exists).
	 * 
	 * @param name key of the associated temporary parameter
	 * @return associated temporary parameter from temporary parameters map
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Returns names of all parameters in temporary parameters map as an
	 * unmodifiable set.
	 * 
	 * @return names of all parameters in temporary parameters map as an
	 *         unmodifiable set
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Returns string identifier which is unique for current user session.
	 * 
	 * @return string identifier which is unique for current user session
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * Stores the given value to temporary parameters map and associates the given
	 * name string with it.
	 * 
	 * @param name  key to be associated with the given temporary parameter value
	 * @param value value to be stored in temporary parameters map
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes a value associated with the given key from temporary parameters map
	 * 
	 * @param name key of the associated temporary parameter
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Writes given data into output stream(that was given to RequestContext in
	 * constructor) using the set encoding or UTF-8 if not set.
	 * 
	 * @param data data to write
	 * @return this RequestContext object
	 * @throws IOException if unable to write to output stream
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data);
		return this;
	}

	/**
	 * Writes len bytes from the specified byte array starting at offset off into
	 * output stream(that was given to RequestContext in constructor) using the set
	 * encoding or UTF-8 if not set.
	 * 
	 * @param text   data
	 * @param offset the start offset in the data
	 * @param len    the number of bytes to write
	 * @return this RequestContext object
	 * @throws IOException if unable to write to output stream
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}

	/**
	 * Writes given text data into output stream(that was given to RequestContext in
	 * constructor) using the set encoding or UTF-8 if not set.
	 * 
	 * @param text text to write
	 * @return this RequestContext object
	 * @throws IOException if unable to write to output stream
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			generateHeader();
		}
		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Generate appropriate header based on already set values of this object.
	 * Writes this header to the set output stream using ISO_8859_1 codepage.
	 * 
	 * @throws IOException if unable to write to output stream
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();

		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if (contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		for (RCCookie cookie : outputCookies) {
			sb.append("Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + "\"");
			if (cookie.getDomain() != null) {
				sb.append("; Domain=" + cookie.getDomain());
			}
			if (cookie.getPath() != null) {
				sb.append("; Path=" + cookie.getPath());
			}
			if (cookie.getMaxAge() != null) {
				sb.append("; Max-Age=" + cookie.getMaxAge());
			}
			if (cookie.isHttpOnly()) {
				sb.append("; HttpOnly");
			}
			sb.append("\r\n");
		}
		sb.append("\r\n"); // header end

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));

		headerGenerated = true;
	}

}
