package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Class that implements a query filter for the specified StudentRecord. The
 * method accepts() will return true if the StudentRecord satisfies all the
 * conditional expressions(provided in the constructor of this object).
 * 
 * @author Mauro Staver
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of conditional expressions used which all have to be satisfied for
	 * accepts() method to return true.
	 */
	private List<ConditionalExpression> list;

	/**
	 * Creates and initializes this QueryFilter with the specified list.
	 * 
	 * @param list List used for initialization.
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}
	
	
	
	/**
	 * Returns true if the specified StudentRecord satisfies all the
	 * conditional expressions(provided in the constructor of this object).
	 */
	@Override
	public boolean accepts(StudentRecord record) {		
		for (ConditionalExpression expr : list) {
			if (!expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral())) {
				return false;
			}
		}
		return true;
	}

}
