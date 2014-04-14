package algo;

import java.util.Date;

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
		//csp.printVariables();
		if (csp.isProblemSolved()) {
			return true;
		}
		
		Variable var = null;
		while ((var = csp.getUnsetVariable()) != null) {
			Domain domain = csp.getVariables().get(var);
			if (domain instanceof IntegerDomain) {
				IntegerDomain dom = (IntegerDomain) domain;
				
				try {
					// deal with a range of values
					for (int i = dom.getLowerBoundary(); i <= dom.getUpperBoundary(); i++) {
							var.setValue(i);
							if (backtrack(csp)) {
								return true;
							}
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
		super.start();
		System.out.println("CSP to solve: \n" + problem);
		boolean result = backtrack(problem);
		endTime = new Date();
		if (result) {
			 System.out.println("Solution:");
			 for (Variable var : problem.getVariables().keySet()) {
				 System.out.println("\t" + var);
			 }
		} else {
			System.out.println("CSP could not be solved");
		}
		System.out.println("Run time: " + getRunTimeString());
	}

	@Override
	public String toString() {
		return "Backtrack [problem=" + problem + "]";
	}
	
	@Override
	public String getName() {
		return "### BACKTRACK ###";
	}

}
