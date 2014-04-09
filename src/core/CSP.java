package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import core.exceptions.DomainException;

public class CSP {
	private Map<Variable, Domain> variables;
	private Set<Domain> domains;
	private List<Constraint> constraints;
	
	public CSP() {
		variables = new HashMap<Variable, Domain>();
		domains = new HashSet<Domain>();
		constraints = new ArrayList<Constraint>();
	}
	
	public void addDomain(Domain domain) {
		domains.add(domain);
	}
	
	public void addVariable(Variable var, Domain domain) {
		domains.add(domain);
		variables.put(var, domain);
	}
	
	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
	}

	public Map<Variable, Domain> getVariables() {
		return variables;
	}

	public Set<Domain> getDomains() {
		return domains;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}
	
	public boolean isProblemSolved() {
		// check domains
		for (Variable var : variables.keySet()) {
			Domain domain = variables.get(var);
			try {
				if (!domain.includes(var)) {
					return false;
				}
			} catch (DomainException e) {
				return false;
			}
		}
		
		// check constraints
		for (Constraint constraint : constraints) {
			//System.out.println(constraint);
			//System.out.println("\t" + constraint.isConstraintValid());
			if (!constraint.isConstraintValid()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Note: an unset variable is a variable whose value is null.
	 * @return Variable object that has not been set, or null.
	 */
	public Variable getUnsetVariable() {
		for (Variable var : variables.keySet()) {
			if (var.getValue() == null) {
				return var;
			}
		}
		return null;
	}
	
	@Deprecated
	public List<Variable> getVariablesFromDomain(Domain domain) {
		return null;
	}

	@Override
	public String toString() {
		String string = "CSP [variables=";
		for (Variable var : variables.keySet()) {
			string += "\n\t" + var;
		}
		string += "\ndomains=";
		for (Domain d : domains) {
			string += "\n\t" + d;
		}
		string += "\nconstraints="; 
		for (Constraint c : constraints) {
			string += "\n\t" + c;
		}
		string += "\n]";
		return string;
	}

	public Domain getDomain(Variable var) {
		return variables.get(var);
	}

	/**
	 * Returns a list of constraints associated with the specified variable.
	 * @param var
	 * @return List<Constraint>
	 */
	public List<Constraint> getConstraints(Variable var) {
		List<Constraint> list = new ArrayList<Constraint>();
		for (Constraint c : constraints) {
			if (c.concerns(var)) {
				list.add(c);
			}
		}
		return list;
	}
	
	
}
