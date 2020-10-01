package hr.fer.zemris.java.hw05.db;

/**
 * An implementation of a Lexer that generates and returns tokens from a given
 * string. The tokens are generated one by one on user request. It reads and
 * generates tokens for: 1) text in quotation marks 2) the word LIKE 3) text
 * which consist of letters until no more letters or 'L' 4) <=, >=, <> 5)
 * everything else as a single char
 * 
 * @author Mauro Staver
 *
 */
public class Lexer {

	private char[] query; // input text
	private int currentIndex = 0; // position of the first unprocessed char
	private boolean noMoreTokens = false; // flag if there are no more tokens to be generated

	/**
	 * Lexer constructor. Constructs and initializes the Lexer.
	 * 
	 * @param query string we want to tokenize.
	 */
	public Lexer(String query) {
		// trim and reduce multiple whitespace signs to one
		this.query = query.trim().replaceAll("\\s+", " ").toCharArray();
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @throws IllegalStateException if there are no more tokens to be generated
	 * @return the next token
	 */
	Token next() {
		if (noMoreTokens) { // no more tokens to be generated
			throw new IllegalStateException("No more tokens to return.");
		}
		if (currentIndex >= query.length) { // EOF reached
			noMoreTokens = true;
			return new Token(TokenType.EOF, null);
		}

		if (Character.isWhitespace(query[currentIndex])) { // skip whitespace
			currentIndex++;
		}

		if (query[currentIndex] == '\"') { // if string
			StringBuilder sb = new StringBuilder();
			currentIndex++;

			while (currentIndex < query.length && query[currentIndex] != '\"') { // read until '"' or EOF
				sb.append(query[currentIndex++]);
			}
			currentIndex++; // because ended at "
			return new Token(TokenType.STRING, sb.toString());
		}
		
		if(query[currentIndex] == 'L') { //special case for LIKE
			StringBuilder sb = new StringBuilder();
			// read all chars until EOF or a nonLetter char
			while (currentIndex < query.length && Character.isLetter(query[currentIndex])) {
				sb.append(query[currentIndex++]);
			}
			return new Token(TokenType.TEXT, sb.toString());
		}
		
		if (Character.isLetter(query[currentIndex])) { // for attributes
			StringBuilder sb = new StringBuilder();
			// read all chars until EOF or a nonLetter char or one before 'L'(because of the
			// operator LIKE)
			while (currentIndex < query.length && Character.isLetter(query[currentIndex])
					&& query[currentIndex] != 'L') {
				sb.append(query[currentIndex++]);
			}
			return new Token(TokenType.TEXT, sb.toString());
		}

		// for operators <=, >= and <> (>> also but it is unsupported)
		if ((query[currentIndex] == '<' || query[currentIndex] == '>')
				&& (query[currentIndex + 1] == '=' || query[currentIndex + 1] == '>')) {
			String operator = "" + query[currentIndex] + query[currentIndex + 1];
			currentIndex += 2; // index after '=' or '>'
			return new Token(TokenType.TEXT, operator);
		}

		// <, >, =, and everything else
		return new Token(TokenType.TEXT, Character.toString(query[currentIndex++]));
	}

}
