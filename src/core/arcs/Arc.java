package core.arcs;

import core.Variable;

public class Arc {
	
	private Variable var_1;
	private Variable var_2;
	
	public Arc(Variable var_1, Variable var_2) {
		this.setVar_1(var_1);
		this.setVar_2(var_2);
	}

	public Variable getVar_1() {
		return var_1;
	}

	public void setVar_1(Variable var_1) {
		this.var_1 = var_1;
	}

	public Variable getVar_2() {
		return var_2;
	}

	public void setVar_2(Variable var_2) {
		this.var_2 = var_2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((var_1 == null) ? 0 : var_1.hashCode());
		result = prime * result + ((var_2 == null) ? 0 : var_2.hashCode());
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
		Arc other = (Arc) obj;
		if (var_1 == null) {
			if (other.var_1 != null)
				return false;
		} else if (!var_1.equals(other.var_1))
			return false;
		if (var_2 == null) {
			if (other.var_2 != null)
				return false;
		} else if (!var_2.equals(other.var_2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Arc [var_1=" + var_1 + ", var_2=" + var_2 + "]";
	}

}
