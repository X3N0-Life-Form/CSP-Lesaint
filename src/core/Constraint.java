package core;

import core.enums.ConstraintType;
import core.exceptions.VariableException;

public class Constraint {
	
	private Variable left;
	private ConstraintType type;
	private Variable right = null;
	private Object value = null;
	
	public Constraint(Variable var, ConstraintType type, Object value) {
		this.left = var;
		this.type = type;
		this.value = value;
	}
	
	public Constraint(Variable left, ConstraintType type, Variable right) {
		this.left = left;
		this.type = type;
		this.right = right;
	}
	
	public boolean isConstraintValid() {
		int comparison;
		if (right != null) {
			comparison = left.compareTo(right);
		} else {
			try {
				comparison = left.compareToValue(value);
			} catch (VariableException e) {
				return false;
			}
		}
		switch (type) {
		case DIFFERENT:
			return comparison != 0;
		case EQUAL:
			return comparison == 0;
		case LESS:
			return comparison < 0;
		case LESS_EQUAL:
			return comparison <= 0;
		case MORE:
			return comparison > 0;
		case MORE_EQUAL:
			return comparison >= 0;
		default:
			break;
		}
		return false;
	}
}
