package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Layout Manager for a calculator.
 * 
 * @author staver
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Map of grid positions for each component.
	 */
	private Map<Component, RCPosition> positions = new HashMap<>();
	private int colNumber = 7;
	private int rowNumber = 5;
	private int gapLength = 0;

	/**
	 * CalcLayout constructor. 
	 * 
	 * @param gapLength gap length to be set between elements.
	 */
	public CalcLayout(int gapLength) {
		this.gapLength = gapLength;
	}

	/**
	 * Unsupported operation.
	 */
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		positions.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calcDimension(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calcDimension(parent, Component::getMinimumSize);
	}

	/**
	 * {@inheritDoc} Calls <code>setBounds(x,y,width,height)</code> on each
	 * component.
	 */
	@Override
	public void layoutContainer(Container parent) {
		Insets parentInsets = parent.getInsets();
		int width = parent.getWidth() - parentInsets.left - parentInsets.right;
		int height = parent.getHeight() - parentInsets.top - parentInsets.bottom;

		double colWidth = ((double) width - (colNumber - 1) * gapLength) / colNumber;
		double rowHeight = ((double) height - (rowNumber - 1) * gapLength) / rowNumber;

		positions.forEach((comp, position) -> {
			int x = (int) Math.round((position.getColumn() - 1) * (colWidth + gapLength)) + parentInsets.left;
			int y = (int) Math.round((position.getRow() - 1) * (rowHeight + gapLength)) + parentInsets.top;

			int nextX;
			int nextY = (int) Math.round(position.getRow() * (rowHeight + gapLength)) + parentInsets.top;

			if (position.getRow() == 1 && position.getColumn() == 1) {
				nextX = (int) Math.round(5 * (colWidth + gapLength)) + parentInsets.left;
			} else {
				nextX = (int) Math.round(position.getColumn() * (colWidth + gapLength)) + parentInsets.left;
			}

			int compWidth = nextX - x - gapLength;
			int compHeight = nextY - y - gapLength;

			comp.setBounds(x, y, compWidth, compHeight);
		});
	}

	/**
	 * {@inheritDoc} The constraint is expected to be a RCPosition object or a
	 * string that can be parsed to RCPosition. The specified component is placed at
	 * position corresponding to the given constraint.
	 * 
	 * @throws IllegalArgumentException if unable to parse constraints
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) {
			throw new NullPointerException("Component and contraints can't be null.");
		}

		RCPosition position;

		if (constraints instanceof String) {
			position = RCPosition.parse((String) constraints); // may throw IllegalArgumentException
		} else if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Unsupported type for constraints!");
		}

		int row = position.getRow();
		int col = position.getColumn();

		if (row < 1 || row > rowNumber || col < 1 || col > colNumber || (row == 1 && (col > 1 && col < 6))) {
			throw new CalcLayoutException("Invalid constraints: (" + row + ", " + col + ")");
		}
		if (positions.containsValue(position)) {
			throw new CalcLayoutException("Component with the specified constraints already exists.");
		}

		positions.put(comp, position);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calcDimension(target, Component::getMaximumSize);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// invalidate
	}

	/**
	 * Interface that models a Dimension getter for some component.
	 * 
	 * @author staver
	 *
	 */
	private interface DimensionGetter {

		/**
		 * Returns the dimension of the given component. Can be used to return preferred
		 * dimension, maximum dimension, minimum dimension...
		 * 
		 * @param comp component whose dimension is to be returned
		 * @return the dimension of the given component
		 */
		Dimension getDimension(Component comp);
	}

	/**
	 * Calculates and returns the max Dimension of all children of the given
	 * container using the given dimension getter.
	 * 
	 * @param parent parent container
	 * @param getter dimension getter - can be used to get preferred dimensions,
	 *               maximum dimensions, minimum dimensions
	 * @return max Dimension of all children of the given container using the given
	 *         dimension getter
	 */
	private Dimension calcDimension(Container parent, DimensionGetter getter) {
		Dimension dim = new Dimension(0, 0);
		int n = parent.getComponentCount();

		for (int i = 0; i < n; i++) {
			Component comp = parent.getComponent(i);
			Dimension compDim = getter.getDimension(comp);

			if (compDim != null) {
				RCPosition position = positions.get(comp);

				if (position.getRow() == 1 && position.getColumn() == 1) { // special case for (1,1)
					dim.width = Math.max(dim.width, (compDim.width - 4 * gapLength) / 5);
				} else { // general case
					dim.width = Math.max(dim.width, compDim.width);
				}

				dim.height = Math.max(dim.height, compDim.height);
			}
		}

		dim.width = colNumber * dim.width + (colNumber - 1) * (gapLength);
		dim.height = rowNumber * dim.height + (rowNumber - 1) * (gapLength);

		Insets parentInsets = parent.getInsets();
		dim.width += parentInsets.left + parentInsets.right;
		dim.height += parentInsets.top + parentInsets.bottom;

		return dim;
	}

}
