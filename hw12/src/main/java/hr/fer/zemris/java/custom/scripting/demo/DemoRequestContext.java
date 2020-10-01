package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Class that demonstrates the use of RequestContext class.
 * 
 * @author staver
 *
 */
public class DemoRequestContext {
	/**
	 * Main method that gets called when the program starts. Creates 3 files and
	 * writes to them using RequestContext object.
	 * 
	 * @param args command line arguments - not used here
	 * @throws IOException if unable to write to file
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Demonstrates how to set up a RequestContext object and write to a file at
	 * given file path(if there is no file, creates one), using the given encoding.
	 * 
	 * @param filePath file path
	 * @param encoding encoding to be used
	 * @throws IOException if unable to write to file
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
	/**
	 * Demonstrates how to set up a RequestContext object and write to a file at
	 * given file path(if there is no file, creates one), using the given encoding.
	 * 
	 * @param filePath file path
	 * @param encoding encoding to be used
	 * @throws IOException if unable to write to file
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));

		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/", false));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", false));// Only at this point will header be created and
																		// written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

}
