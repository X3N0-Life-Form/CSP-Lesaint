package core;

public class Variable {
	
	private VariableType type;
	private Object value;
	
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

}
