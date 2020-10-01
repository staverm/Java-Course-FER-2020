package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Program that demonstrates the use of SmartScriptEngine. Used files are
 * specified in the homework text.
 * 
 * @author staver
 *
 */
public class SmartScriptEngineDemo {

	/**
	 * Main method that gets called when the program starts. Simply uncomment some
	 * line(assuming you have the required .smscr files in the right path).
	 * 
	 * @param args command line arguments - not used here
	 */
	public static void main(String[] args) {
		demo1("osnovni.smscr");
		// demo1("fibonacci.smscr");
		// demo1("fibonaccih.smscr");
		// demo2();
		// demo3();
	}

	/**
	 * Executes script at given path with all RequestContext's maps being empty.
	 * 
	 * @param filePath path to .smscr file
	 */
	private static void demo1(String filePath) {
		String documentBody = "";
		try {
			documentBody = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (IOException e) {
			System.out.println("Unable to read from given file.");
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Executes script "zbrajanje.smscr", with added entries in parameters map.
	 */
	private static void demo2() {
		String documentBody = "";
		try {
			documentBody = new String(Files.readAllBytes(Paths.get("zbrajanje.smscr")));
		} catch (IOException e) {
			System.out.println("Unable to read from given file.");
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Executes script "brojPoziva.smscr", with added entries in
	 * persistentParameters map.
	 */
	private static void demo3() {
		String documentBody = "";
		try {
			documentBody = new String(Files.readAllBytes(Paths.get("brojPoziva.smscr")));
		} catch (IOException e) {
			System.out.println("Unable to read from given file.");
			return;
		}
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

}
