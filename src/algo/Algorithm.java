package algo;

import java.util.ArrayList;
import java.util.List;

import core.CSP;

public abstract class Algorithm {

	protected CSP problem;
	protected List<Algorithm> before;
	
	public Algorithm(CSP csp) {
		this.problem = csp;
		before = new ArrayList<Algorithm>();
	}
	
	/**
	 * 
	 * @return true if this algorithm's CSP has been solved.
	 */
	//public boolean isSolved() {
	//	return problem.isProblemSolved();
	//}
	
	/**
	 * Add an algorithm that will be executed before this one.
	 * @param algo
	 */
	public void addBeforeAlgorithm(Algorithm algo) {
		before.add(algo);
	}
	
	public void start() throws AlgorithmException {
		for (Algorithm algo : before) {
			algo.start();
		}
		System.out.println(getName());
	}
	
	public abstract String getName();
	
	//TODO:timestamp things
}
