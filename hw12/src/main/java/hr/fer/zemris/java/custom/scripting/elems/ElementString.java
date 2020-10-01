package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class that represents a string in an expression.
 * 
 * @author Mauro Staver
 *
 */
public class ElementString extends Element{
	
	private String value;
	
	/**
	 * Constructor. Constructs and initializes a new ElementString object with the
	 * given value.
	 * 
	 * @param value used for initialization.
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		StringBuilder sb = new StringBuilder();
		sb.append('\"');
			
		for(int i = 0; i < value.length(); ++i) {
			switch(value.charAt(i)) { //handles escapes
			case '\\':
				sb.append("\\\\");
				break;
			case '\"':
				sb.append("\\\"");
				break;
			case 10:
				sb.append("\n");
				break;
			case 13:
				sb.append("\r");
				break;
			case 9:
				sb.append("\t");
				break;
			default: sb.append(value.charAt(i));
			}	
		}
		
		sb.append('\"');
		return sb.toString();
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof ElementString) {
			return this.getValue().equals(((ElementString) other).getValue());
		}
		return false;
	}
}
