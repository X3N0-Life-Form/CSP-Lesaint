package algo;

import core.CSP;
import core.Domain;
import core.IntegerDomain;
import core.Variable;
import core.exceptions.VariableException;

public class Backtrack extends Algorithm {

	public Backtrack(CSP csp) {
		super(csp);
	}

	// gen & test?
	public boolean backtrack(CSP csp) throws AlgorithmException {
		//System.out.println("Recap:");
		//System.out.println(this);
		if (csp.isProblemSolved()) {
			return true;
		}
		
		Variable var = null;
		while ((var = csp.getUnsetVariable()) != null) {
			Domain domain = csp.getVariables().get(var);
			//System.out.println("Domain: " + domain + "\n  var: " + var);
			if (domain instanceof IntegerDomain) {
				IntegerDomain dom = (IntegerDomain) domain;
				
				try {
					// deal with a range of values
					//System.out.println("testing values " + dom.getLowerBoundary()
					//		+ " through " + dom.getUpperBoundary());
					for (int i = dom.getLowerBoundary(); i <= dom.getUpperBoundary(); i++) {
							var.setValue(i);
							//System.out.println("\t" + var);
							if (backtrack(csp)) {
								return true;
							}
					}
					
					// deal with a list of values (if it exists)
					if (dom.getValidValues() != null) {
						//System.out.println("Testing values " + dom.getValidValues());
						for (Integer value : dom.getValidValues()) {
							var.setValue(value);
							//System.out.println("\t" + var);
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
		System.out.println("CSP to solve: \n" + problem);
		if (backtrack(problem)) {
			 System.out.println("Solution:");
			 for (Variable var : problem.getVariables().keySet()) {
				 System.out.println("\n\t" + var);
			 }
		} else {
			System.out.println("CSP could not be solved");
		}
		
	}

	@Override
	public String toString() {
		return "Backtrack [problem=" + problem + "]";
	}

}
