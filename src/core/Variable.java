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
		if (type == VariableType.INTEGER || value instanceof Integer) {
			this.value = value;
		} else {
			throw new VariableException("Invalid object type, expected " + type);
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
	public int compareTo(Variable o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int compareToValue(Object value2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
