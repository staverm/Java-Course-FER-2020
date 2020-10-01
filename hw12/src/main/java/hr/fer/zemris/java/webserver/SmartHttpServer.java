package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.*;

/**
 * Class that implements an http server. It listens to requests at specified
 * port and generates http responses to appropriate http requests.
 * 
 * @author staver
 *
 */
public class SmartHttpServer {
	private String address; // server's ip addres
	private String domainName; // server's domain name
	private int port; // port on which server listens to request
	private int workerThreads; // number of worker threads for processing requests
	private int sessionTimeout; // session timeout time
	private Map<String, String> mimeTypes = new HashMap<>(); //maps mime types
	private Map<String, IWebWorker> workersMap = new HashMap<>(); //maps worker names to workers
	private ServerThread serverThread; //main server thread
	private SessionCollector sessionCollector; // used to remove invalid sessions
	private ExecutorService threadPool;
	private Path documentRoot; //path to webroot folder
	private Map<String, SessionMapEntry> sessions = 
			new HashMap<String, SmartHttpServer.SessionMapEntry>(); //maps session ids to sessions
	private Random sessionRandom = new Random(); // used to generate session ids

	/**
	 * Class that represents a session. It is identified with a session id(sid) and
	 * has a map that is used to store information of that session.
	 * 
	 * @author staver
	 *
	 */
	private static class SessionMapEntry {
		private String sid; // session id
		private String host; // session host
		private long validUntil; // epoch time when this session will become invalid
		private Map<String, String> map; // map for storing information belonging to this session

		/**
		 * SessionMapEntry constructor. Constructs a new SessionMapEntry object and
		 * initializes it with the given argument.
		 * 
		 * @param sid        session id
		 * @param host       session host
		 * @param validUntil epoch time until this session is valid
		 * @param map        map for storing information belonging to this
		 *                   session(should be thread-safe)
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

		/**
		 * Returns session's id.
		 * 
		 * @return session's id.
		 */
		public String getSid() {
			return sid;
		}

		/**
		 * Returns session's host.
		 * 
		 * @return session's host.
		 */
		public String getHost() {
			return host;
		}

		/**
		 * Returns epoch time when this session will become invalid.
		 * 
		 * @return epoch time of death (time that this session will become invalid)
		 */
		public long getValidUntil() {
			return validUntil;
		}

		public Map<String, String> getMap() {
			return map;
		}

		/**
		 * Sets this session's moment of death: epoch time when the session should
		 * become invalid.
		 * 
		 * @param validUntil epoch time when the session should become invalid
		 */
		public void setValidUntil(long validUntil) {
			this.validUntil = validUntil;
		}
	}

	/**
	 * Main method that gets called when the program starts. It initializes the
	 * server and starts it.
	 * 
	 * @param args command lines arguments - 1 argument expected: path to
	 *             server.properties file
	 */
	public static void main(String[] args) {
		String serverConfig = "./config/server.properties"; // path to server.properties file
		SmartHttpServer S = new SmartHttpServer(serverConfig);
		S.start();
	}

	/**
	 * SmartHttpServer constructor. Creates a new server and initializes it with a
	 * .properties file whose path is given as argument.
	 * 
	 * @param configFileName path to .properties file used to initialize the server
	 */
	public SmartHttpServer(String configFileName) {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(configFileName));
		} catch (IOException e) {
			System.out.println("Unable to read from file: " + configFileName);
			e.printStackTrace();
		}

		address = properties.getProperty("server.address");
		domainName = properties.getProperty("server.domainName");
		port = Integer.parseInt(properties.getProperty("server.port"));
		workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
		documentRoot = Paths.get(properties.getProperty("server.documentRoot"));

		serverThread = new ServerThread();
		sessionCollector = new SessionCollector();

		// READ mime.properties and fill mimeTypes map
		String mimeConfig = properties.getProperty("server.mimeConfig");
		Properties mimeProperties = new Properties();
		try {
			mimeProperties.load(new FileReader(mimeConfig));
		} catch (IOException e) {
			System.out.println("Unable to read from file: " + configFileName);
			e.printStackTrace();
		}
		for (String key : mimeProperties.stringPropertyNames()) {
			mimeTypes.put(key, mimeProperties.getProperty(key));
		}

		// READ workers.properties and fill workersMap
		String workersPropPath = properties.getProperty("server.workers");
		Properties workersProperties = new Properties();
		try {
			workersProperties.load(new FileReader(workersPropPath));
		} catch (IOException e) {
			System.out.println("Unable to read from file: " + configFileName);
			e.printStackTrace();
		}
		for (String path : workersProperties.stringPropertyNames()) {
			String fqcn = workersProperties.getProperty(path);
			try {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				if (workersMap.containsKey(path)) {
					throw new IllegalArgumentException("Multiple lines with same path in workers.properties");
				}
				workersMap.put(path, iww);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Starts the server if not already started.
	 */
	protected synchronized void start() {
		if (!serverThread.isAlive()) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread.start();

			sessionCollector.setDaemon(true);
			sessionCollector.start();
		}
	}

	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		sessionCollector.interrupt();
		threadPool.shutdown();
	}

	/**
	 * Daemon thread used to periodically(every 5 minutes) remove invalid
	 * sessions(sessions who reached timeout).
	 * 
	 * @author staver
	 *
	 */
	protected class SessionCollector extends Thread {
		/**
		 * Iterates through sessions map every 5 minutes and removes invalid sessions.
		 */
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(300000); // sleep 5 min
					for (String sid : sessions.keySet()) {
						if (sessions.get(sid).getValidUntil() < Instant.now().getEpochSecond()) {
							sessions.remove(sid);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * Main server thread that listens for connections on some port. When requests
	 * arrive, they get assigned to some worker from the thread pool.
	 * 
	 * @author staver
	 *
	 */
	protected class ServerThread extends Thread {
		private ServerSocket serverSocket;

		@Override
		public void run() {
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));

				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}

			} catch (IOException e) {
				System.out.println("Unable to create socket.");
				e.printStackTrace();
			}

		}
	}

	/**
	 * A Runnable that serves client requests: parses HTTP request, processes it and
	 * sends a response.
	 * 
	 * @author staver
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		private Socket csocket; //client socket
		private InputStream istream; //input stream from client
		private OutputStream ostream; //output stream to client
		private String version; //http version
		private String method; //http method (GET, POST etc.)
		private String host = null; //host property sent in request header
		private Map<String, String> params = new HashMap<String, String>(); // parameters
		private Map<String, String> tempParams = new HashMap<String, String>(); // temporary parameters
		private Map<String, String> permParams = new HashMap<String, String>(); // permanent parameters
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>(); //cookies to be sent
		private String SID; // session id
		private RequestContext context = null; //context for sending http response

		/**
		 * ClientWorker constructor. Constructs a new ClientWorker and initializes it
		 * with the given Socket.
		 * 
		 * @param csocket socket to which the client is connected to.
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		/**
		 * Serves client's request: parses HTTP request, processes it and sends a
		 * response.
		 */
		@Override
		public void run() {
			try {
				istream = new BufferedInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				List<String> request = readRequest();
				String[] firstLine = request.isEmpty() ? null : request.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				version = firstLine[2].toUpperCase();
				String requestedPathString = firstLine[1];
				if (!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
					sendError(400, "Bad request");
					return;
				}

				for (String line : request) { // extract host
					line = line.replaceAll("\\s+", "");
					String[] split = line.split(":");
					if (split[0].equals("Host")) {
						host = split.length > 1 ? split[1] : null;
					}
				}
				if (host == null) {
					host = domainName;
				}

				checkSession(request);

				String[] reqPathSplit = requestedPathString.split("\\?");
				String path = reqPathSplit[0];
				String paramString = reqPathSplit.length > 1 ? reqPathSplit[1] : null;
				parseParameters(paramString);

				internalDispatchRequest(path, true);

				// end connection with client
				ostream.flush();
				ostream.close();
				csocket.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * Checks received cookies for session id. If no session id cookies are found:
		 * creates new session. If a session id cookie is found: checks server's session
		 * map if the session is valid, if not - creates new session. After the session
		 * has been established, a reference to the session's map is used to initializes
		 * the permanent parameters map(permParams).
		 * 
		 * @param request header - list of strings, where each string is one header line
		 */
		private void checkSession(List<String> request) {
			String sidCandidate = null;
			for (String line : request) {
				line = line.replaceAll("\\s+", "");
				if (line.startsWith("Cookie:")) {
					String[] cookieSplit = line.substring(7).split(";");
					String[] nameParam = cookieSplit[0].split("=");

					if (nameParam[0].equals("sid")) {
						sidCandidate = nameParam[1].replaceAll("\"", ""); // remove quotations
						break;
					}
				}
			}

			synchronized (sessions) {
				if (sidCandidate == null) {
					newSession();
				} else {
					SessionMapEntry associated = sessions.get(sidCandidate);

					if (associated != null && associated.getHost().equals(host)) {
						if (Instant.now().getEpochSecond() > associated.getValidUntil()) {
							sessions.remove(sidCandidate);
							newSession();
						} else { // VALID
							associated.setValidUntil(Instant.now().getEpochSecond() + sessionTimeout);
							SID = associated.getSid();
						}
					} else {
						newSession();
					}
				}

				permParams = sessions.get(SID).getMap();
			}
		}

		/**
		 * Creates a random session id, uses it to create a new session and an output
		 * cookie.
		 */
		private void newSession() {
			String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				sb.append(abc.charAt(sessionRandom.nextInt(abc.length())));
			}
			SID = sb.toString(); // current session
			sessions.put(SID, new SessionMapEntry(SID, host, Instant.now().getEpochSecond() + sessionTimeout,
					new ConcurrentHashMap<>()));
			outputCookies.add(new RCCookie("sid", SID, null, host, "/", true));
			System.out.println("New session created with id:" + SID);
		}

		/**
		 * Dispatch request that serves the resource specified by given urlPath argument
		 * to the client.
		 * 
		 * @param urlPath    (relative)path to wanted resource
		 * @param directCall flag that specifies whether the given path was requested
		 *                   directly by client
		 * @throws Exception if any exception occurs(for example while writing to output
		 *                   stream)
		 */
		private void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			Path requestedPath = documentRoot.resolve(urlPath.substring(1));

			if (directCall && (urlPath.equals("/private") || urlPath.startsWith("/private/"))) {
				sendError(404, "File not found");
				return;
			}

			if (!requestedPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}

			if (urlPath.startsWith("/ext/")) { // load worker
				String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.substring(5);
				Class<?> referenceToClass;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				} catch (ClassNotFoundException ex) {
					sendError(404, "File not found");
					return;
				}
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;
				iww.processRequest(getContext());
				return;
			}

			if (workersMap.containsKey(urlPath)) { // load worker from workersMap
				workersMap.get(urlPath).processRequest(getContext());
				return;
			}

			if (!Files.isRegularFile(requestedPath) || !Files.isReadable(requestedPath)) {
				sendError(404, "File not found");
				return;
			}

			String extension = urlPath.substring(urlPath.lastIndexOf('.') + 1);
			String mimeType = mimeTypes.get(extension);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			if (extension.equals("smscr")) {
				runSmartScript(requestedPath);
				return;
			}

			getContext(); // initializes context if not already
			context.setMimeType(mimeType);
			context.setStatusCode(200);
			context.setStatusText("OK");

			context.write(Files.readAllBytes(requestedPath)); // write response
		}

		/**
		 * First initializes context if it is null, then returns it. If context isn't
		 * null, simply returns it.
		 * 
		 * @return this worker's context (initializes it if null)
		 */
		private RequestContext getContext() {
			context = context != null ? context
					: new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			return context;
		}

		/**
		 * Executes SmartScript file at given path.
		 * 
		 * @param path path to SmartScript file
		 */
		private void runSmartScript(Path path) {
			String documentBody = "";
			try {
				documentBody = new String(Files.readAllBytes(path));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

			// create engine and execute it
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), getContext()).execute();
		}

		/**
		 * Fills parameters map by parsing given string which is of format:
		 * "name1=value1&name2=value2...".
		 * 
		 * @param paramString string of parameters
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) {
				return;
			}
			String[] entries = paramString.split("\\&");

			for (String entry : entries) {
				String[] entrySplit = entry.split("=");
				params.put(entrySplit[0], entrySplit[1]);
			}
		}

		/**
		 * Sends an HTTP response with the given status code and status text, with no
		 * body. Used to send errors back to clients.
		 * 
		 * @param statusCode status code to be sent
		 * @param statusText status text to be sent
		 * @throws IOException if unable to write to output stream
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: SmartHttpServer\r\n"
					+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
					+ "\r\n").getBytes(StandardCharsets.US_ASCII));
			ostream.flush();
			ostream.close();
			csocket.close();
		}

		/**
		 * Reads HTTP request header and returns it as a list of string where each
		 * string is one header line.
		 * 
		 * @return HTTP request header: list of strings where each string is one header
		 *         line
		 * @throws IOException if unable to read from input stream (istream)
		 */
		private List<String> readRequest() throws IOException {
			byte[] requestBytes = requestToBytes();

			List<String> headers = new ArrayList<>();

			if (requestBytes == null) {
				return headers;
			}
			String requestStr = new String(requestBytes, StandardCharsets.US_ASCII);

			String currentLine = null;
			for (String s : requestStr.split("\n")) {
				if (s.isEmpty()) {
					break;
				}

				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Simple automata that reads HTTP request header into byte array.
		 * 
		 * @return HTTP request header as an array of bytes
		 * @throws IOException if unable to read from input stream (istream)
		 */
		private byte[] requestToBytes() throws IOException {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
	}

}
