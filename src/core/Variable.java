package core;

import core.enums.VariableType;
import core.exceptions.VariableException;

public class Variable implements Comparable<Variable> {
	
	private VariableType type;
	private Object value;
	private String name;
	
	public Variable(String name, VariableType type) {
		this.setName(name);
		this.type = type;
	}
	
	public VariableType getType() {
		return type;
	}
	
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

	@Override
	public int compareTo(Variable anotherVariable) {
		switch (type) {
		case INTEGER:
			return ((Integer) this.value).compareTo((Integer) anotherVariable.value);
		case TEST_UNSUPPORTED:
			return 0;
		}
		return 0;
	}

	public int compareToValue(Object value2) throws VariableException {
		switch (type) {
		case INTEGER:
			if (value2 instanceof Integer) {
				return ((Integer) this.value).compareTo((Integer) value2);
			} else {
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

}
