package hr.fer.zemris.java.hw06.shell.commands;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void parseArgumentsEscapeTest() {
		List<String> expected = new ArrayList<>();
		expected.add("firstArgument");
		expected.add("\"String \\ Argument\"");
		expected.add("thirdArgument");
		
		String arguments = " firstArgument \"\\\"String \\\\ Argument\\\"\" thirdArgument";

		List<String> actual = Util.parseArguments(arguments);
		compareLists(expected, actual);		
	}
	
	@Test
	void parseArgumentsGeneralTest() {
		List<String> expected = new ArrayList<>();
		expected.add("command");
		expected.add("argument1");
		expected.add("path/to some/file.txt");
		expected.add("argument3");
		
		String arguments = "command argument1 \"path/to some/file.txt\" argument3";

		List<String> actual = Util.parseArguments(arguments);
		compareLists(expected, actual);		
	}
	
	@Test
	void parseArgumentsInvalidStringTest() {
		String arguments = "firstArgument \"invalid string\"noSpaceAfterDoubleQuotes";
		assertThrows(IllegalArgumentException.class, () -> Util.parseArguments(arguments));
	}
	
	
	
	/**
	 * Helper method used for comparing Lists.
	 * 
	 * @param expected expected list
	 * @param actual actual list
	 */
	private void compareLists(List<String> expected, List<String> actual) {
		assertEquals(expected.size(), actual.size());
		for(int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
	}

}
