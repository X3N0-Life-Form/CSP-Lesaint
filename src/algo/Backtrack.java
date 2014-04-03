package algo;

import core.CSP;
import core.Domain;
import core.IntegerDomain;
import core.Variable;
import core.exceptions.VariableException;

public class Backtrack implements Algorithm {
	
	private CSP problem;
	
	public Backtrack(CSP csp) {
		this.problem = csp;
	}

	public boolean backtrack(CSP csp) throws AlgorithmException {
		Variable var = null;
		while ((var = csp.getUnsetVariable()) != null) {
			Domain domain = csp.getVariables().get(var);
			if (domain instanceof IntegerDomain) {
				IntegerDomain dom = (IntegerDomain) domain;
				
				try {
					// deal with a range of values
					for (int i = dom.getLowerBoundary(); i <= dom.getUpperBoundary(); i++) {
							var.setValue(i);
							if (backtrack(csp))
								return true;
					}
					
					// deal with a list of values (if it exists)
					if (dom.getValidValues() != null) {
						for (Integer value : dom.getValidValues()) {
							var.setValue(value);
							if (backtrack(csp))
								return true;
						}
					}
				} catch (VariableException e) {
					throw new AlgorithmException(e.getMessage());
				}
			} else {
				throw new AlgorithmException("Unsupported domain class: " + domain.getClass());
			}
		}
		return false;
	}
	
	@Override
	public void start() throws AlgorithmException {
		backtrack(problem);
	}

}
