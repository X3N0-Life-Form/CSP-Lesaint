package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		if (!domains.contains(domain)) {
			domains.add(domain);
		}
		if (!variables.containsKey(var)) {
			variables.put(var, domain);
		}
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
	
	
}
