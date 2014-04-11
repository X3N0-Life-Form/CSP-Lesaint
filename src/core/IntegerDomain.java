package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import core.enums.VariableType;
import core.exceptions.DomainException;
import core.exceptions.VariableException;

public class IntegerDomain extends Domain implements Comparable<IntegerDomain> {

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
		} else if (isValueForbidden(value, var)) {
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
	
	/**
	 * 
	 * @param value
	 * @param var
	 * @return true if the value is forbidden.
	 */
	public boolean isValueForbidden(int value, Variable var) {
		if (variableSpecificForbiddenValues.get(var) != null
				&& variableSpecificForbiddenValues.get(var).contains(value)) {
			return true;
		} else if (variableSpecificForbiddenRanges.get(var) != null) {
			for (Integer[] range : variableSpecificForbiddenRanges.get(var)) {
				if (range[0] <= value && range[1] >= value) {
					return true;
				}
			}
			return false;
		} else {
			return false;
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
		String string = "IntegerDomain [lowerBoundary=" + lowerBoundary
				+ ", upperBoundary=" + upperBoundary + ", validValues="
				+ validValues + "\nforbiddenValues=" + forbiddenValues
				+ "\nforbiddenRanges=" + forbiddenRanges
				+ "\nvariableSpecificForbiddenRanges=";
		for (Variable var : variableSpecificForbiddenRanges.keySet()) {
			string += "\n\t" + var;
				for (Integer[] range : variableSpecificForbiddenRanges.get(var)) {
					string += "\n\t\t[" + range[0] + ".." + range[1] + "]";
				}
		}
				string += "\nvariableSpecificForbiddenValues="
				+ variableSpecificForbiddenValues + "]";
		return string;
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

	@Override
	public int compareTo(IntegerDomain domain) {
		return this.size() - domain.size();
	}

	@Override
	public int size() {
		int size = (upperBoundary - lowerBoundary);
		if (validValues != null) {
			size += validValues.size();
		}
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((forbiddenRanges == null) ? 0 : forbiddenRanges.hashCode());
		result = prime * result
				+ ((forbiddenValues == null) ? 0 : forbiddenValues.hashCode());
		result = prime * result + lowerBoundary;
		result = prime * result + upperBoundary;
		result = prime * result
				+ ((validValues == null) ? 0 : validValues.hashCode());
		result = prime
				* result
				+ ((variableSpecificForbiddenRanges == null) ? 0
						: variableSpecificForbiddenRanges.hashCode());
		result = prime
				* result
				+ ((variableSpecificForbiddenValues == null) ? 0
						: variableSpecificForbiddenValues.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntegerDomain other = (IntegerDomain) obj;
		if (forbiddenRanges == null) {
			if (other.forbiddenRanges != null)
				return false;
		} else if (!forbiddenRanges.equals(other.forbiddenRanges))
			return false;
		if (forbiddenValues == null) {
			if (other.forbiddenValues != null)
				return false;
		} else if (!forbiddenValues.equals(other.forbiddenValues))
			return false;
		if (lowerBoundary != other.lowerBoundary)
			return false;
		if (upperBoundary != other.upperBoundary)
			return false;
		if (validValues == null) {
			if (other.validValues != null)
				return false;
		} else if (!validValues.equals(other.validValues))
			return false;
		if (variableSpecificForbiddenRanges == null) {
			if (other.variableSpecificForbiddenRanges != null)
				return false;
		} else if (!variableSpecificForbiddenRanges
				.equals(other.variableSpecificForbiddenRanges))
			return false;
		if (variableSpecificForbiddenValues == null) {
			if (other.variableSpecificForbiddenValues != null)
				return false;
		} else if (!variableSpecificForbiddenValues
				.equals(other.variableSpecificForbiddenValues))
			return false;
		return true;
	}

}
