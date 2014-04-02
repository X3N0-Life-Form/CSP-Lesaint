package core;

import core.enums.VariableType;
import core.exceptions.DomainException;

public class IntegerDomain extends Domain {

	private int lowerBoundary = 0;
	private int upperBoundary = Integer.MAX_VALUE;
	
	public IntegerDomain() {}
	
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
		return false;
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
