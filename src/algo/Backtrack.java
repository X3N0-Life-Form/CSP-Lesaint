package algo;

import core.CSP;

public class Backtrack implements Algorithm {
	
	private CSP problem;
	
	public Backtrack(CSP csp) {
		this.problem = csp;
	}

	public void backtrack(CSP csp) {
		
	}
	
	@Override
	public void start() {
		backtrack(problem);
	}

}
