package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents a state of the Turtle used for drawing lines. The state
 * is described by position, direction, color and effectiveUnitLength.
 * 
 * @author Mauro Staver
 *
 */
public class TurtleState {

	private Vector2D position; // current position
	private Vector2D direction; // current direction
	private Color color;
	private double effectiveUnitLength;

	/**
	 * Creates and returns a new TurtleState which is a copy of this object.
	 * 
	 * @return
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, effectiveUnitLength);
	}

	/**
	 * Creates and initializes a new TurtleState using the specified values.
	 * 
	 * @param position            initial position(Vector2D).
	 * @param direction           initial direction(Vector2D).
	 * @param color               initial color for drawing lines.
	 * @param effectiveUnitLength initial EffectiveUnitLength.
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveUnitLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveUnitLength = effectiveUnitLength;
	}

	/**
	 * Returns the Position vector of this TurtleState object.
	 * 
	 * @return the Position vector(Vector2D) of this TurtleState object.
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Returns the Direction vector of this TurtleState object.
	 * 
	 * @return the Direction vector(Vector2D) of this TurtleState object.
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Returns the EffectiveUnitLength of this TurtleState object.
	 * 
	 * @return the EffectiveUnitLength of this TurtleState object.
	 */
	public double getEffectiveUnitLength() {
		return effectiveUnitLength;
	}

	/**
	 * EffectiveUnitLength setter. Sets the effectiveUnitLength of this TurtleState
	 * object to the specified value.
	 * 
	 * @param effectiveUnitLength value to be set.
	 */
	public void setEffectiveUnitLength(double effectiveUnitLength) {
		this.effectiveUnitLength = effectiveUnitLength;
	}

	/**
	 * Color getter.
	 * 
	 * @return the color of this TurtleState object.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Color setter. Sets the color of this TurtleState object to the specified
	 * color.
	 * 
	 * @param color color to be set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
