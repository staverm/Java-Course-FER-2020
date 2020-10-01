package hr.fer.zemris.java.hw03;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * Class that tests the SmartScriptParser using the file specified by a command
 * line argument. The program parses the file into a hierarchy of nodes and then
 * parses that hierarchy back to String. The program then parses that String
 * into a hierarchy of nodes and compares the DocumentNodes of the two said
 * hierarchies.
 * 
 * @author Mauro Staver
 *
 */
public class SmartScriptTester {

	/**
	 * Main method that gets called when the program starts. The method parses the
	 * file into a hierarchy of nodes and then processes that hierarchy back to
	 * String. The program then parses that String into a hierarchy of nodes and
	 * compares the DocumentNodes of the two said hierarchies.
	 * 
	 * @param args command line arguments. 1 argument expected: the path to a .txt
	 *             file used as input for the Parser.
	 */
	public static void main(String[] args) {

		

		try {
			String filepath = args[0];
			
			//has \n at the end 
			String docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
			SmartScriptParser parser = null;
			
			try {
				parser = new SmartScriptParser(docBody);
			} catch (SmartScriptParserException e) {
				System.out.println(e.getMessage());
				System.out.println("Unable to parse document!");
				System.exit(-1);
			} catch (Exception e) {
				System.out.println("If this line ever executes, you have failed this class!");
				System.exit(-1);
			}
			
			DocumentNode document = parser.getDocumentNode(); //has 5 children because of the \n at the end
			String originalDocumentBody = document.toString();

			SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
			DocumentNode document2 = parser2.getDocumentNode();// now document and document2 should be structurally
																// identical trees

			boolean same = document.equals(document2); // ==> "same" must be true

			System.out.println(same + "\n");
			System.out.println(originalDocumentBody); // should write something like original

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

}
