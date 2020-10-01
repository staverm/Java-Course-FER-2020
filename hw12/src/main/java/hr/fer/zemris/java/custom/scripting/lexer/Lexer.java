package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * An implementation of a Lexer that generates and returns tokens from a given
 * string. The tokens are generated one by one on user request. The user can
 * choose between two modes(or states) of token generation: TEXT and TAG. These
 * states describe rules on how to tokenize the given text and are specified in
 * the homework assignment text.
 * 
 * @author Mauro Staver
 *
 */
public class Lexer {
	private char[] data; // input text
	private Token token; // the current token
	private int currentIndex = 0; // position of the first unprocessed char
	private LexerState state = LexerState.TEXT; // state for generating the next token

	/**
	 * Lexer constructor. Constructs and initializes the Lexer.
	 * 
	 * @param text string we want to tokenize.
	 */
	public Lexer(String text) {
		data = text.toCharArray(); // throws NullPointerException if given String is null
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @throws LexerException if error occurs
	 * @return the next token
	 */
	public Token nextToken() throws LexerException {
		// if EOF already returned
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("EOF already reached!");
		}

		// if EOF reached
		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		// TEXT STATE
		if (state == LexerState.TEXT) {
			if (data[currentIndex] == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') { // if
																												// "{$"
				currentIndex += 2;
				token = new Token(TokenType.OPEN_TAG, null);
				return token;
			}

			StringBuilder sb = new StringBuilder();

			while (currentIndex < data.length && !(data[currentIndex] == '{' && currentIndex + 1 < data.length
					&& data[currentIndex + 1] == '$')) { // read until EOF or {$

				if (data[currentIndex] == '\\') { // if '\'

					if (currentIndex + 1 < data.length && // if next char is not EOF and is either '\' or '{'
							(data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{')) {
						// append char at currentIndex+1
						sb.append(data[++currentIndex]);
					} else { // EOF or something thats not a '{' or '\'
						throw new LexerException("Invalid escape sequence.");
					}

				} else { // normal letters
					sb.append(data[currentIndex]);
				}
				currentIndex++;
			}

			token = new Token(TokenType.TEXT, sb.toString());
			return token;
		}

		// remove leading whitespace
		while (currentIndex < data.length) {
			if (Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			} else {
				break;
			}
		}

		// TAG STATE
		if (data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '}') { // if "$}"
			currentIndex += 2;
			token = new Token(TokenType.CLOSE_TAG, null);
			return token;
		}

		if (data[currentIndex] == '@') { // if function
			currentIndex++;
			token = new Token(TokenType.FUNCTION, readVariableName());
			return token;
		}

		if (data[currentIndex] == '\"') { // if string
			StringBuilder sb = new StringBuilder();
			currentIndex++;

			while (currentIndex < data.length && data[currentIndex] != '\"') { // read until EOF or ending "

				if (data[currentIndex] == '\\') { // if '\'
					if (currentIndex + 1 < data.length) {// if next char is not EOF
						switch (data[currentIndex + 1]) {
						case '\\':
							sb.append(data[currentIndex + 1]);
							break;
						case '\"':
							sb.append(data[currentIndex + 1]);
							break;
						case 'n':
							sb.append((char) 10); // \n
							break;
						case 'r':
							sb.append((char) 13); // \r
							break;
						case 't':
							sb.append((char) 9); // \t
							break;
						default:
							throw new LexerException("Invalid escape sequence.");
						}

						currentIndex++;
					} else {// EOF
						throw new LexerException("Invalid escape sequence.");
					}

				} else { // normal chars
					sb.append(data[currentIndex]);
				}
				currentIndex++;
			}

			currentIndex++; // because ended at "
			token = new Token(TokenType.STRING, sb.toString());
			return token;
		}

		if (data[currentIndex] == '-' && // if minus and following char is digit
				currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
			token = readNumberToken();
			return token;
		}

		if (Character.isDigit(data[currentIndex])) { // if digit
			token = readNumberToken();
			return token;
		}

		if (Character.isLetter(data[currentIndex])) { // if letter
			token = new Token(TokenType.VARIABLE, readVariableName());
			return token;
		}

		// SYMBOL CASE
		char c = data[currentIndex];
		// supported operators
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '=') {
			token = new Token(TokenType.SYMBOL, Character.toString(data[currentIndex]));
			currentIndex++;
			return token;
		} else { // IF ALL ABOVE FAILS
			throw new LexerException("Invalid operator.");
		}
	}

	/**
	 * Tries to read numbers of type double or int from the current position in
	 * input text. If successful it returns a Token of type DOUBLE or INTEGER with
	 * the read number.
	 * 
	 * @return Token that contains number
	 * @throws LexerException if can't read number
	 */
	private Token readNumberToken() {
		StringBuilder sb = new StringBuilder();

		if (data[currentIndex] == '-') { // if starts with minus
			sb.append('-');
			currentIndex++;
		}

		boolean dotFlag = false;

		// while not EOF and current char is a digit or '.'
		while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
			if (data[currentIndex] == '.' && !dotFlag) { // if first '.'

				sb.append('.');
				dotFlag = true; // append only one '.'
				currentIndex++;
			} else if (Character.isDigit(data[currentIndex])) { // if digit
				sb.append(data[currentIndex++]);

			} else { // more than one '.'
				throw new LexerException("Can't read number");
			}
		}

		return dotFlag ? new Token(TokenType.DOUBLE, sb.toString()) : new Token(TokenType.INTEGER, sb.toString());
	}

	/**
	 * Tries to read a variable name from the current position in input text.
	 * Variable name has to start with a letter and can contain only letters,
	 * numbers and underscores. If successful returns the String containing the
	 * variable name
	 * 
	 * @return String that contains the variable name
	 * @throws LexerException if can't read variable name
	 */
	private String readVariableName() {
		StringBuilder sb = new StringBuilder();

		if (Character.isLetter(data[currentIndex])) { // if starts with letter

			while (currentIndex < data.length // if not EOF and current char is letter or digit or '_' repeat
					&& (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
							|| data[currentIndex] == '_')) {

				sb.append(data[currentIndex++]);
			}

		} else {
			throw new LexerException("Invalid variable name.");
		}

		return sb.toString();
	}

	/**
	 * Returns the last generated token. Doesn't start the generation of the next
	 * token. Can be called multiple times.
	 * 
	 * @return the last generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets Lexer state to the specified state.
	 * 
	 * @param state state to set the Lexer
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException("Can't set state to null.");
		}

		this.state = state;
	}
}
