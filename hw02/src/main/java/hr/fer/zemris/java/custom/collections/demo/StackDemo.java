package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class that shows an example use of the ObjectStack.
 * It calculates expressions in postfix representation.
 * @author Mauro Staver
 *
 */
public class StackDemo {

	/**
	 * Main method that gets called when the program starts.
	 * @param args Command line arguments.
	 * One Argument is expected, a string that holds an expression in postfix representation.
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			System.out.println("Wrong number of arguments");
		}else {
			String[] expression = args[0].split(" ");
			ObjectStack Stack = new ObjectStack();
			
			for(String element : expression) { 
				try {
					Integer operand = Integer.parseInt(element);
					Stack.push(operand); //element is a number
				}catch(NumberFormatException ex) { //element is an operator
					int rightOperand = (int)Stack.pop();
					int leftOperand = (int)Stack.pop();
					
					Integer result = 0;
					switch(element) {
						case "+":
							result = leftOperand + rightOperand;
							break;
						case "-":
							result = leftOperand - rightOperand;
							break;
						case "*":
							result = leftOperand * rightOperand;
							break;
						case "/":
							if(rightOperand == 0) {
								System.out.println("Division by zero");
								return;
							}
							result = leftOperand / rightOperand;
							break;
						case "%":
							result = leftOperand % rightOperand;
							break;
						default: 
							System.out.println("Expression is invalid");
							return;
					}
					
					Stack.push(result);
				}
			}
			
			if(Stack.size() != 1) {
				System.out.println("Expression is invalid");
				return;
			}else {
				System.out.println(Stack.pop());
			}
			
		}
	}
}
