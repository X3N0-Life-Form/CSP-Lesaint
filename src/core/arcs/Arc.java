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

	
}
