package algo;

import java.util.List;

import core.CSP;
import core.Constraint;
import core.IntegerDomain;
import core.Variable;
import core.enums.ConstraintType;
import core.exceptions.VariableException;

public class Backtrack_2 extends Backtrack {

	public Backtrack_2(CSP csp) {
		super(csp);
	}
	
	@Override
	public String getName() {
		return "### Backtrack 2 ###";
	}
	
	/**
	 * Test & Generate
	 * @throws AlgorithmException 
	 */
	@Override
	public boolean backtrack(CSP csp) throws AlgorithmException {
		if (csp.isProblemSolved()) {
			return true;
		}
		
		Variable var = null;
		Variable notThisOne = null;
		while ((var = csp.getUnsetVariable(notThisOne)) != null) {
			try {
				IntegerDomain dom = (IntegerDomain) csp.getVariables().get(var);
				for (int i = dom.getLowerBoundary(); i <= dom.getUpperBoundary(); i++) {
					var.setValue(i);
					if (i % (dom.size() * 0.1) == 0
							|| i == dom.getUpperBoundary()
							|| i == dom.getLowerBoundary()) {
						System.out.println(var.getName() + " = " + i);
					}
					if (toReset != null) {
						toReset.resetValue();
						toReset = null;
					}
					if (constraintsMightGetValidated(csp, var)) {
						if (backtrack(csp)) {
							return true;
						}
					} else {
						// no point in continuing to loop
						notThisOne = var; //not working
						break;
					}
				}
				
				if (toReset == null) {
					toReset = var;
				} else {
					toReset.resetValue();
					toReset = null;
				}
			} catch (VariableException e) {
				throw new AlgorithmException(e.getMessage());
			}
		}
		return false;
	}

	/**
	 * Check whether the specified Variable's constraints have a snowball's
	 * chance in hell to get validated some day.
	 * @param csp
	 * @param var
	 * @return true if there is.
	 */
	public boolean constraintsMightGetValidated(CSP csp, Variable var) {
		int v_value = (int) var.getValue();
		List<Constraint> constraints = csp.getConstraints(var);
		for (Constraint con : constraints) {
			if (con.getRight() == null) {
				int c_value = (int) con.getValue();
				if (!yup(v_value, c_value, con.getType())) {
					return false;
				}
			} else {
				Variable other_var = con.getRight();
				if (var.getName().equals(other_var.getName())) {
					// oops, picked the wrong one
					other_var = con.getLeft();
				}
				// if we are dealing with an uninitialized variable, that's great
				if (other_var.getValue() == null) {
					return true;
				}
				
				int other_value = (int) other_var.getValue();
				// assuming the other variable value remains static for the time being
				if (!yup(v_value, other_value, con.getType())) {
					return false;
				}
			}
		}
		// nothing objectionable was found
		return true;
	}

	/**
	 * Checks whether that constraint can still be validated, assuming an
	 * increasing v_value.
	 * @param v_value Variable value
	 * @param static_value Unchanging value (from either a Constraint, or another Variable).
	 * @param type
	 * @return true if that constraint can get validated.
	 */
	private boolean yup(int v_value, int static_value, ConstraintType type) {
		switch (type) {
		case DIFFERENT:
			// you can always be different, unless you hit the goddamn domain boundary
			return true;
		case EQUAL:
			return v_value <= static_value;
		case INF:
			return v_value < static_value;
		case INF_EQUAL:
			return v_value <= static_value;
		case SUP:
		case SUP_EQUAL:
			// ever increasing value = someday you'll be greater than the constraint
			// (unless it's the upper boundary)
			return true;
		}
		return false;
	}

}
