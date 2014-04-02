package algo;

import core.CSP;
import core.Domain;
import core.Variable;

public class Backtrack implements Algorithm {
	
	private CSP problem;
	
	public Backtrack(CSP csp) {
		this.problem = csp;
	}

	public boolean backtrack(CSP csp) {
		Variable varUnset = null;
		while ((varUnset = csp.getUnsetVariable()) != null) {
			Domain domain = csp.getVariables().get(varUnset);
		}
		return false;
	}
	
	@Override
	public void start() {
		backtrack(problem);
	}

}
