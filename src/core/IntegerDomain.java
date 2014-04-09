package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.enums.VariableType;
import core.exceptions.DomainException;
import core.exceptions.VariableException;

public class IntegerDomain extends Domain {

	private int lowerBoundary = 0;
	private int upperBoundary = Integer.MAX_VALUE;
	/**
	 * Warning: null by default.
	 */
	private List<Integer> validValues = null;
	
	private List<Integer> forbiddenValues;
	private List<Integer[]> forbiddenRanges;
	
	private Map<Variable, List<Integer[]>> variableSpecificForbiddenRanges;
	private Map<Variable, List<Integer>> variableSpecificForbiddenValues;
	
	/**
	 * Constructs an empty Domain.
	 */
	public IntegerDomain() {
		variableSpecificForbiddenRanges = new HashMap<Variable, List<Integer[]>>();
		variableSpecificForbiddenValues = new HashMap<Variable, List<Integer>>();
		forbiddenValues = new ArrayList<Integer>();
		forbiddenRanges = new ArrayList<Integer[]>();
	}
	
	/**
	 * Constructs a Domain with a list of valid values.
	 * @param validValues
	 */
	public IntegerDomain(List<Integer> validValues) {
		this();
		this.validValues = validValues;
	}
	
	/**
	 * Constructs a Domain with boundaries.
	 * @param lowerBoundary
	 * @param upperBoundary
	 */
	public IntegerDomain(int lowerBoundary, int upperBoundary) {
		this();
		this.setLowerBoundary(lowerBoundary);
		this.setUpperBoundary(upperBoundary);
	}
	
	@Override
	public boolean includes(Variable var) throws DomainException {
		if (var.getType() != VariableType.INTEGER) {
			throw new DomainException("Invalid domain, expected " + VariableType.INTEGER
					+ "found " + var.getType());
		} else if (var.getValue() == null) {
			return false;
		}
		
		int value = (int) var.getValue();
		if (value < lowerBoundary) {
			return false;
		} else if (value > upperBoundary) {
			return false;
		} else if (validValues != null && !validValues.contains(value)) {
			return false;
		} else if (isValueForbidden(value)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Tests whether the specified value is forbidden, according to
	 * forbidden range and value list. Note that this does not test
	 * domain inclusion, but explicit restrictions to the Domain.
	 * ie. if the specified value is not a valid value due to the
	 * Domain's range but isn't otherwise restricted, it isn't a forbidden
	 * value.
	 * @param value
	 * @return true if the value is forbidden.
	 */
	public boolean isValueForbidden(int value) {
		if (forbiddenValues.contains(value)) {
			return true;
		}
		for (Integer[] range : forbiddenRanges) {
			if (value >= range[0] && value <= range[1]) {
				return true;
			}
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

	/**
	 * Warning: null by default.
	 * @return List of valid values.
	 */
	public List<Integer> getValidValues() {
		return validValues;
	}
	
	public void addForbiddenValue(int value) {
		forbiddenValues.add(value);
	}
	
	public void addForbiddenRange(int lowerBoundary, int upperBoundary) {
		Integer[] range = new Integer[2];
		range[0] = lowerBoundary;
		range[1] = upperBoundary;
		forbiddenRanges.add(range);
	}
	
	/**
	 * Note: we assume that var is of the correct type & domain.
	 * @param lowerBoundary
	 * @param upperBoundary
	 * @param var
	 */
	public void addForbiddenRange(int lowerBoundary, int upperBoundary, Variable var) {
		Integer[] range = new Integer[2];
		range[0] = lowerBoundary;
		range[1] = upperBoundary;
		if (variableSpecificForbiddenRanges.get(var) == null) {
			variableSpecificForbiddenRanges.put(var, new LinkedList<Integer[]>());
		}
		variableSpecificForbiddenRanges.get(var).add(range);
	}
	
	public void addForbiddenValue(int value, Variable var) {
		if (variableSpecificForbiddenValues.get(var) == null) {
			variableSpecificForbiddenValues.put(var, new LinkedList<Integer>());
		}
		variableSpecificForbiddenValues.get(var).add(value);
	}

	@Override
	public String toString() {
		return "IntegerDomain [lowerBoundary=" + lowerBoundary
				+ ", upperBoundary=" + upperBoundary + ", validValues="
				+ validValues + "]";
	}

	/**
	 * 
	 * @param value
	 * @return true if the specified value is included in this Domain.
	 * @throws DomainException
	 * @see {@link Domain}.includes()
	 */
	public boolean includes(int value) throws DomainException {
		Variable mockVar = new Variable("mock", VariableType.INTEGER);
		try {
			mockVar.setValue(value);
			return includes(mockVar);
		} catch (VariableException e) {
			return false;
		}
	}

}
