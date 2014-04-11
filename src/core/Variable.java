package core;

import core.enums.VariableType;
import core.exceptions.VariableException;

public class Variable implements Comparable<Variable> {
	
	private VariableType type;
	private Object value;
	private String name;
	
	/**
	 * Instantiate a Variable. Note that, by default, it has no value.
	 * Use setValue() to give it a value.
	 * @param name
	 * @param type
	 */
	public Variable(String name, VariableType type) {
		this.setName(name);
		this.type = type;
	}
	
	public VariableType getType() {
		return type;
	}
	
	/**
	 * Sets this Variable's value.
	 * @param value
	 * @throws VariableException If the value is null or of an incorrect type.
	 */
	public void setValue(Object value) throws VariableException {
		if (value == null) {
			throw new VariableException("Passed a null reference");
		} else if (type == VariableType.INTEGER && value instanceof Integer) {
			this.value = value;
		} else {
			throw new VariableException("Invalid object type, expected " + type + ", received " + value.getClass());
		}
	}
	
	public Object getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Compare the Variable's values. A Variable with a value is considered
	 * to be greater than a Variable without one.
	 * <br />In the case of two null values, the variables' names are compared instead.
	 */
	@Override
	public int compareTo(Variable anotherVariable) {
		if (this.value == null && anotherVariable.value == null) {
			return this.name.compareTo(anotherVariable.name);
		} else if (this.value != null && anotherVariable.value == null) {
			return 1;
		} else if (this.value == null && anotherVariable.value != null) {
			return -1;
		} else {
			switch (type) {
			case INTEGER:
				return ((Integer) this.value).compareTo((Integer) anotherVariable.value);
			case TEST_UNSUPPORTED:
				return 0;
			}
			return 0;
		}
	}

	public int compareToValue(Object value2) throws VariableException {
		switch (type) {
		case INTEGER:
			if (value2 instanceof Integer) {
				return ((Integer) this.value).compareTo((Integer) value2);
			} else if (value2 == null) {
				throw new VariableException("Received a null value.");
			}else {
				throw new VariableException("Invalid value type, expected " + type + ", received " + value2.getClass());
			}
		case TEST_UNSUPPORTED:
			return 0;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Variable [type=" + type + ", value=" + value + ", name=" + name
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * Note that this doesn't check the variable's value.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
