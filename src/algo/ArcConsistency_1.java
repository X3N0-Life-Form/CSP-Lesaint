package algo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.CSP;
import core.Constraint;
import core.Domain;
import core.IntegerDomain;
import core.Variable;
import core.arcs.Arc;
import core.exceptions.DomainException;

public class ArcConsistency_1 extends Algorithm {
	
	protected Set<Arc> arcs;
	private Map<Domain, List<Integer>> toForbid;

	public ArcConsistency_1(CSP csp) {
		super(csp);
		arcs = new HashSet<Arc>();
		toForbid = new HashMap<Domain, List<Integer>>();
		prepareToForbid();
		prepareArcs();
	}
	
	private void prepareToForbid() {
		for (Domain domain : problem.getDomains()) {
			toForbid.put(domain, new LinkedList<Integer>());
		}
	}
	
	private void prepareArcs() {
		for (Variable var_1 : problem.getVariables().keySet()) {
			for (Variable var_2 : problem.getVariables().keySet()) {
				arcs.add(new Arc(var_1, var_2));
			}
		}
	}
	
	
	public void AC1(CSP csp) {
		boolean finished = false;
		do {
			finished = true;
			for (Arc arc : arcs) {
				try {
					if (checkArc(arc)) {
						//delete / save values to avoid TODO
						finished = false;
					}
				} catch (DomainException e) {
					// what do if some domain is fucked up? TODO
				}
			}
		} while(!finished);
	}

	/**
	 * 
	 * @param arc
	 * @return
	 * @throws DomainException 
	 */
	protected boolean checkArc(Arc arc) throws DomainException {
		Variable var_1 = arc.getVar_1();
		Variable var_2 = arc.getVar_2();
		Domain d1 = problem.getDomain(var_1);
		Domain d2 = problem.getDomain(var_2);
		List<Constraint> c_list_1 = problem.getConstraints(var_1);
		List<Constraint> c_list_2 = problem.getConstraints(var_2);
		
		// check var_1's constraints
		for (Constraint c : c_list_1) {
			if (c.concerns(var_2)) {
				return verifyDomains(c, d1, d2);
			}
		}
		
		// check var_2's constraints
		for (Constraint c : c_list_2) {
			if (c.concerns(var_1)) {
				return verifyDomains(c, d2, d1);
			}
		}
		
		// check value-based constraints?
		
		return false;
	}

	/**
	 * Note: returns false for unsupported Variable types.
	 * We assume that the domains passed to this methods are
	 * correct, ie. var_1 & var_2 are part of d_left & d_right, respectively.
	 * @param c
	 * @param d_left
	 * @param d_right
	 * @return true there are unsupported d_left values in d_right,
	 * according to the specified constraint
	 * @throws DomainException 
	 */
	protected boolean verifyDomains(Constraint c, Domain d_left,
			Domain d_right) throws DomainException {
		switch(c.getLeft().getType()) {
		case INTEGER:
			IntegerDomain d1 = (IntegerDomain) d_left;
			IntegerDomain d2 = (IntegerDomain) d_right;
			return verifyIntegerDomains(c, d1, d2);
		case TEST_UNSUPPORTED:
			return false;
		}
		return false;
	}

	/**
	 * Verify whether something's got to be done.
	 * @param c
	 * @param d_left
	 * @param d_right
	 * @return true there are unsupported d_left values in d_right,
	 * according to the specified constraint
	 * @throws DomainException 
	 */
	protected boolean verifyIntegerDomains(Constraint c, IntegerDomain d_left,
			IntegerDomain d_right) throws DomainException {
		// check every value in this domain's range
		for (int i = d_left.getLowerBoundary(); i < d_left.getUpperBoundary(); i++) {
			if (!d_right.includes(i) || verifyConstraint(c, d_left, d_right, i)) {
				flagForForbiddation(i, d_right);
				return true;
			}
		}

		/* again, forget about this
		if (d_left.getValidValues() != null) {
			// check every value in this domain's value list
			for (Integer value : d_left.getValidValues()) {
				if (!d_right.includes(value) || verifyConstraint(c, d_left, d_right, value)) {
					return true;
				}
			}
		}
		*/

		return false;
	}

	/**
	 * 
	 * @param c
	 * @param d_left
	 * @param d_right
	 * @param d_leftValue
	 * @return
	 */
	protected boolean verifyConstraint(Constraint c, IntegerDomain d_left,
			IntegerDomain d_right, Integer d_leftValue) {
		//note: forget about the damn validValueList thingy, it's not required
		switch (c.getType()) {
		// nothing to do
		case DIFFERENT:
			flagForForbiddation(d_leftValue, d_right);
			return true;
		case EQUAL:
		case INF_EQUAL:
		case SUP_EQUAL:
			// don't care, should already be covered by inclusion
			return false;
		// stuff to do
		case SUP:
			for (int i = d_right.getLowerBoundary(); i <= d_right.getUpperBoundary(); i++) {
				if (!d_right.isValueForbidden(i) && i == d_leftValue) {
					flagForForbiddation(i, d_right);
					return true;
				}
			}
			break;
		case INF:
			for (int i = d_right.getUpperBoundary(); i >= d_right.getLowerBoundary(); i--) {
				// the first not forbidden value should get forbidden
				if (!d_right.isValueForbidden(i) && i == d_leftValue) {
					flagForForbiddation(i, d_right);
					return true;
				}
			}
			break;
		}
		return false;
	}

	/**
	 * Flags a value so that it will be added to the forbidden values
	 * when updateForbiddenValues() is called.
	 * @param value
	 * @param d_right
	 */
	protected void flagForForbiddation(int value, IntegerDomain d_right) {
		toForbid.get(d_right).add(value);
	}
	
	/**
	 * Adds new forbidden values to each domain, then clear the lists of values to forbid.
	 */
	protected void updateForbiddenValues() {
		for (Domain domain : toForbid.keySet()) {
			List<Integer> list = toForbid.get(domain);
			for (Integer value : list) {
				((IntegerDomain) domain).addForbiddenValue(value);
			}
			list.clear();
		}
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	
	public Set<Arc> getArcs() {
		return arcs;
	}

}
