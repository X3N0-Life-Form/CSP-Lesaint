package algo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.CSP;

public abstract class Algorithm {

	protected CSP problem;
	protected List<Algorithm> before;
	protected Date startTime = null;
	protected Date endTime = null;
	
	public Algorithm(CSP csp) {
		this.problem = csp;
		before = new ArrayList<Algorithm>();
	}
	
	/**
	 * 
	 * @return true if this algorithm's CSP has been solved.
	 */
	public boolean isSolved() {
		return problem.isProblemSolved();
	}/**/
	
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
		startTime = new Date();
	}
	
	/**
	 * Get how much time it took the algorithm to run in String format.
	 * @return String time in s, ms or an error message.
	 */
	public String getRunTimeString() {
		if (startTime == null) {
			return "No start time was recorded.";
		} else if (endTime == null) {
			return "No end time was recorded.";
		} else {
			long time = endTime.getTime() - startTime.getTime();
			if (time < 1000) {
				return time + " ms";
			} else {
				String string = (time / 1000) + "." + (time % 1000) + " s";
				return string;
			}
		}
	}
	
	public abstract String getName();
	
}
