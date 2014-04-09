package algo;

import core.CSP;

public abstract class Algorithm {

	protected CSP problem;
	
	public Algorithm(CSP csp) {
		this.problem = csp;
	}
	
	//TODO:timestamp things
	
	public abstract void start() throws AlgorithmException;
}
