package core;

import core.enums.ConstraintType;
import core.exceptions.VariableException;

public class Constraint {
	
	private Variable left;
	private ConstraintType type;
	private Variable right = null;
	private Object value = null;
	
	public Constraint(Variable var, ConstraintType type, Object value) throws VariableException {
		switch (var.getType()) {
		case INTEGER:
			if (!(value instanceof Integer)) {
				throw new VariableException("Invalid type, expected " + Integer.class + ", found " + value.getClass());
			}
			break;
		default:
			throw new VariableException("Unsupported type: " + var.getType());
		}
		
		this.left = var;
		this.type = type;
		this.value = value;
	}
	
	public Constraint(Variable left, ConstraintType type, Variable right) throws VariableException {
		if (left.getType() != right.getType()) {
			throw new VariableException("Mismatching variable types: " + left.getType() + " != " + right.getType());
		}
		
		this.left = left;
		this.type = type;
		this.right = right;
	}
	
	public boolean isConstraintValid() {
		int comparison;
		if (left == null || left.getValue() == null) {
			return false;
		} else if (right != null) {
			//System.out.println(left + " compared to " + right);
			comparison = left.compareTo(right);
		} else {
			try {
				//System.out.println(left.getValue() + " compared to " + value);
				comparison = left.compareToValue(value);
			} catch (VariableException e) {
				return false;
			}
		}
		//System.out.println("comp result=" + comparison + "(type=" + type + ")");
		switch (type) {
		case DIFFERENT:
			return comparison != 0;
		case EQUAL:
			return comparison == 0;
		case INF:
			return comparison < 0;
		case INF_EQUAL:
			return comparison <= 0;
		case SUP:
			return comparison > 0;
		case SUP_EQUAL:
			return comparison >= 0;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Constraint [left=" + left + ", type=" + type + ", right="
				+ right + ", value=" + value + "]";
	}

	/**
	 * 
	 * @param var
	 * @return true if this Constraints concerns the specified Variable.
	 */
	public boolean concerns(Variable var) {
		if (left.equals(var) || (right != null && right.equals(var))) {
			return true;
		} else {
			return false;
		}
	}

	public Variable getLeft() {
		return left;
	}
	
	public Variable getRight() {
		return right;
	}

	public ConstraintType getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}
}
