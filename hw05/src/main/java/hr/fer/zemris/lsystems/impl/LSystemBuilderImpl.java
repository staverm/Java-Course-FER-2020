package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that models objects which can be configured and then by calling the
 * method build() a Lindmayer system is returned with the specified
 * configuration.
 * 
 * @author Mauro Staver
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Class that models a Lindermayer system. It draws fractals using productions
	 * as a template.
	 * 
	 * @author Mauro Staver
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Draws the fractal using the specified painter. The first parameter is the
		 * fractal's depth.
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context context = new Context();

			TurtleState state = new TurtleState(origin.copy(), new Vector2D(1, 0).rotated(angle), Color.BLACK,
					unitLength * Math.pow(unitLengthDegreeScaler, arg0)); // initial values

			context.pushState(state);

			String appliedProductions = generate(arg0);

			for (int i = 0; i < appliedProductions.length(); i++) {
				Character c = appliedProductions.charAt(i);
				Command command = commands.get(c); // get command for char C from dictionary

				if (command != null) { // if no command: continue
					command.execute(context, arg1); // execute command
				}
			}
		}

		/**
		 * Returns the string generated by applying productions to the axiom depth
		 * number of times, where depth is provided as an argument.
		 */
		@Override
		public String generate(int arg0) {
			StringBuilder sb = new StringBuilder(axiom);

			for (int k = 0; k < arg0; k++) {
				String prevString = sb.toString(); // string from previous iteration, originally = axiom
				sb.setLength(0); // reset StringBuilder

				for (int i = 0; i < prevString.length(); i++) { // for all chars in prevString
					Character c = prevString.charAt(i);
					String production = productions.get(c);

					if (production == null) {
						sb.append(c);
					} else {
						sb.append(production);
					}
				}
			}

			return sb.toString();
		}

	}

	private Dictionary<Character, String> productions;
	private Dictionary<Character, Command> commands;
	private double unitLength = 0.1;
	private double unitLengthDegreeScaler = 1;
	private Vector2D origin = new Vector2D(0, 0);
	private double angle = 0;
	private String axiom = "";

	public LSystemBuilderImpl() {
		productions = new Dictionary<>();
		commands = new Dictionary<>();
	}

	/**
	 * Returns a new LSystem object initialized with the value to which this
	 * LSystemBuilder is configured to.
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Initializes a LSystemBuilder using the specified array of Strings. Each
	 * string in the array is one directive.
	 * 
	 * @throws IllegalArgumentException if unable to parse the given strings.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for (int i = 0; i < arg0.length; i++) {
			String s = arg0[i].trim().replaceAll("\\s+", " "); // trim and reduce multiple whitespace signs to one
			String[] inputSplit = s.split(" ");

			if (inputSplit.length < 2) {
				continue;
			}

			try {
				switch (inputSplit[0]) {
				case "origin":
					setOrigin(Double.parseDouble(inputSplit[1]), Double.parseDouble(inputSplit[2]));
					break;
				case "angle":
					setAngle(Double.parseDouble(inputSplit[1]));
					break;
				case "unitLength":
					setUnitLength(Double.parseDouble(inputSplit[1]));
					break;
				case "unitLengthDegreeScaler":
					// pass as argument the original string without the "unitLengthDegreeScaler"
					// prefix
					double argument = parseArgument(s.substring(s.indexOf(' ') + 1));
					setUnitLengthDegreeScaler(argument);
					break;
				case "command":
					String commandArgument;
					if (inputSplit.length == 3) { // push and pop have this format
						commandArgument = inputSplit[2];
					} else {
						commandArgument = inputSplit[2] + " " + inputSplit[3];
					}
					registerCommand(inputSplit[1].charAt(0), commandArgument);
					break;
				case "axiom":
					setAxiom(inputSplit[1]);
					break;
				case "production":
					registerProduction(inputSplit[1].charAt(0), inputSplit[2]);
					break;
				default:
					throw new IllegalArgumentException("Invalid input.");
				}

			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid argument.");
			}
		}

		return this;
	}

	/**
	 * Parses the given string which represents a fraction or a double into a double
	 * and returns it. The string is expected to have the format: "x / y" or just
	 * "x" where x and y are numbers(with or without decimals) and whitespace(one or
	 * more) between 'x','/' and 'y' is allowed.
	 * 
	 * @param s string to parse
	 * @return the double value represented by the string argument.
	 * @throws NumberFormatException if unable to parse
	 */
	private double parseArgument(String str) {
		str.replaceAll("\\s+", ""); // remove spaces
		int divisionSign = str.indexOf('/'); // index of '/'

		if (divisionSign != -1) {
			double result = Double.parseDouble(str.substring(0, divisionSign)) // dividend
					/ Double.parseDouble(str.substring(divisionSign + 1)); // divisor

			return result;
		}

		return Double.parseDouble(str); // if no '/'
	}

	/**
	 * Registers the command provided as arguments. Example: 'F' "draw 1"
	 * 
	 * @throws IllegalArgumentException if unable to parse the given arguments.
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		arg1 = arg1.trim().replaceAll("\\s+", " ");
		String[] commandString = arg1.split(" ", 2);
		// commandString[0] is the action and commandString[1] is the argument

		Command command;

		try {
			switch (commandString[0]) {
			case "draw":
				command = new DrawCommand(Double.parseDouble(commandString[1]));
				break;
			case "skip":
				command = new SkipCommand(Double.parseDouble(commandString[1]));
				break;
			case "scale":
				command = new ScaleCommand(Double.parseDouble(commandString[1]));
				break;
			case "rotate":
				command = new RotateCommand(Double.parseDouble(commandString[1]));
				break;
			case "push":
				command = new PushCommand();
				break;
			case "pop":
				command = new PopCommand();
				break;
			case "color":
				command = new ColorCommand(Color.decode("#" + commandString[1]));
				break;
			default:
				throw new IllegalArgumentException("Invalid command.");
			}
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Invalid command");
		}

		commands.put(arg0, command);
		return this;
	}

	/**
	 * Registers the production provided as arguments.
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	/**
	 * Sets the Angle to the specified value.
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}

	/**
	 * Sets the Origin to the specified (x,y) coordinates.
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Sets the UnitLength to the specified value.
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	/**
	 * Sets the UnitLengthDegreeScaler to the specified value.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
