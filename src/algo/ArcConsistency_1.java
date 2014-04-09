package algo;

import java.util.HashSet;
import java.util.List;
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

	public ArcConsistency_1(CSP csp) {
		super(csp);
		arcs = new HashSet<Arc>();
		prepareArcs();
	}
	
	protected void prepareArcs() {
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
		
		// check value-based constraints
		
		return false;
	}

	/**
	 * Note: returns false for unsupported Variable types
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
	 * 
	 * @param c
	 * @param d_left
	 * @param d_right
	 * @return true there are unsupported d_left values in d_right,
	 * according to the specified constraint
	 * @throws DomainException 
	 */
	protected boolean verifyIntegerDomains(Constraint c, IntegerDomain d_left,
			IntegerDomain d_right) throws DomainException {
		for (int i = d_left.getLowerBoundary(); i < d_left.getUpperBoundary(); i++) {
			if (!d_right.includes(i)) {
				return true;
			}
		}
		for (Integer value : d_left.getValidValues()) {
			if (!d_right.includes(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

}
