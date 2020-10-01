package hr.fer.zemris.java.hw03.prob1;

/**
 * An implementation of a Lexer that generates and returns tokens from a given string.
 * The tokens are generated one by one on user request.
 * The user can choose between two modes(or states) of token generation: BASIC and EXTENDED.
 * These states describe rules on how to tokenize the given text
 *  and are specified in the homework assignment text.
 * @author Mauro Staver
 *
 */
public class Lexer {
	
	private char[] data;      //input text
	private Token token;      // the current token
	private int currentIndex = 0; // position of the first unprocessed char
	private LexerState state = LexerState.BASIC; //state for generating the next token
	
	/**
	 * Lexer constructor.
	 * Constructs and initializes the Lexer.
	 * @param text string we want to tokenize.
	 */
	public Lexer(String text) {
		data = text.toCharArray(); //throws NullPointerException if given String is null
	}
	
	/**
	 * Generates and returns the next token.
	 * @throws LexerException if error occurs
	 * @return the next token
	 */
	public Token nextToken() throws LexerException{
		//if EOF already returned
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("EOF already reached!");
		}
		
		//remove leading whitespace
		while(currentIndex < data.length) {
			if(Character.isWhitespace(data[currentIndex])) {
				currentIndex++;
			}else {
				break;
			}
		}
		
		//if EOF reached
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;	
		}
		
		//EXTENDED
		if(state == LexerState.EXTENDED) {
			if(data[currentIndex] == '#') { //if #
				currentIndex++;
				token = new Token(TokenType.SYMBOL, '#');
				return token;
			}
			
			StringBuilder sb = new StringBuilder();
			
			//read until whitespace or # or EOF
			while(currentIndex<data.length && !Character.isWhitespace(data[currentIndex]) && data[currentIndex]!='#') {
				sb.append(data[currentIndex++]);
			}
			
			token = new Token(TokenType.WORD, sb.toString());
			return token;
		}
		
		//BASIC	
		if(Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\'){	 //WORD case
			StringBuilder sb = new StringBuilder();
			
			//read until EOF or the first nonLetter char thats not '\' 
			while(currentIndex<data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex]=='\\')) {
				
				if(data[currentIndex] == '\\') { //if '\'
					
					if(currentIndex+1<data.length && //if next char is not EOF and is either '\' or Digit 
							(data[currentIndex+1]=='\\' || Character.isDigit(data[currentIndex+1]))) {
						//append char at currentIndex+1
						sb.append(data[++currentIndex]); 	
					}else { // EOF or something thats not a number or \
						throw new LexerException("Invalid escape sequence.");
					}	
					
				}else { //normal letters 
					sb.append(data[currentIndex]);
				}
				currentIndex++;	
			}
			
			token = new Token(TokenType.WORD, sb.toString());
			return token;
			
		}else if(Character.isDigit(data[currentIndex])) { //DIGIT case
			
			StringBuilder sb = new StringBuilder();
			
			//read until EOF or the first nonDigit char
			while(currentIndex<data.length && Character.isDigit(data[currentIndex])) { 
				sb.append(data[currentIndex++]);
			}
			
			try {//parse to Long and return
				long value = Long.parseLong(sb.toString());
				token = new Token(TokenType.NUMBER, value);
				return token;
			}catch(NumberFormatException ex) {
				throw new LexerException("Too large number!");
			}
			
		}else { //SYMBOL CASE
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return token;
		}
	}
	

	/**
	 * Returns the last generated token. Doesn't start the generation of the next token.
	 * Can be called multiple times.
	 * @return the last generated token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets Lexer state to the specified state.
	 * @param state state to set the Lexer
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("Can't set state to null.");
		}
		
		this.state = state;
	}
	
}
