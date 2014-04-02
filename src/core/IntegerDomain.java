package core;

import java.util.List;

import core.enums.VariableType;
import core.exceptions.DomainException;

public class IntegerDomain extends Domain {

	private int lowerBoundary = 0;
	private int upperBoundary = Integer.MAX_VALUE;
	private List<Integer> validValues = null;
	
	public IntegerDomain(List<Integer> validValues) {
		this.validValues = validValues;
	}
	
	public IntegerDomain(int lowerBoundary, int upperBoundary) {
		this.setLowerBoundary(lowerBoundary);
		this.setUpperBoundary(upperBoundary);
	}
	
	@Override
	public boolean includes(Variable var) throws DomainException {
		if (var.getType() != VariableType.INTEGER) {
			throw new DomainException("Invalid domain, expected " + VariableType.INTEGER
					+ "found " + var.getType());
		}
		
		int value = (int) var.getValue();
		if (value < lowerBoundary) {
			return false;
		} else if (value > upperBoundary) {
			return false;
		} else if (validValues != null && !validValues.contains(value)) {
			return false;
		} else {
			return true;
		}
	}

	public int getLowerBoundary() {
		return lowerBoundary;
	}

	public void setLowerBoundary(int lowerBoundary) {
		this.lowerBoundary = lowerBoundary;
	}

	public int getUpperBoundary() {
		return upperBoundary;
	}

	public void setUpperBoundary(int upperBoundary) {
		this.upperBoundary = upperBoundary;
	}

}
