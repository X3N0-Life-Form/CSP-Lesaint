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
	
	/**
	 * Verifies all constraints.
	 * @return true if all Constraints are validated.
	 */
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
		return getUnsetVariable(null);
	}
	
	public Variable getUnsetVariable(Variable butNotThisOne) {
		for (Variable var : variables.keySet()) {
			if (var.getValue() == null && var != butNotThisOne) {
				return var;
			}
		}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((constraints == null) ? 0 : constraints.hashCode());
		result = prime * result + ((domains == null) ? 0 : domains.hashCode());
		result = prime * result
				+ ((variables == null) ? 0 : variables.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CSP other = (CSP) obj;
		if (constraints == null) {
			if (other.constraints != null)
				return false;
		} else if (!constraints.equals(other.constraints))
			return false;
		if (domains == null) {
			if (other.domains != null)
				return false;
		} else if (!domains.equals(other.domains))
			return false;
		if (variables == null) {
			if (other.variables != null)
				return false;
		} else if (!variables.equals(other.variables))
			return false;
		return true;
	}

	public void printVariables() {
		for (Variable var : variables.keySet()) {
			System.out.println(var);
		}
	}

	/**
	 * Lookup a Variable according to its name.
	 * @param varName
	 * @return Requested Variable or null.
	 */
	public Variable getVariable(String varName) {
		for (Variable var : variables.keySet()) {
			if (var.getName().equals(varName)) {
				return var;
			}
		}
		return null;
	}
	
}
