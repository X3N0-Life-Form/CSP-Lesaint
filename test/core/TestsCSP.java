package core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.enums.ConstraintType;
import core.enums.VariableType;

public class TestsCSP {
	
	private CSP csp;
	private Domain d1;
	private Domain d2;
	private Variable var_1;
	private Variable var_2;
	private Constraint c1;
	private Constraint c2;

	@Before
	public void setUp() throws Exception {
		csp = new CSP();
		d1 = new IntegerDomain(0, 200);
		d1 = new IntegerDomain(-5, -1);
		var_1 = new Variable("var_1", VariableType.INTEGER);
		var_2 = new Variable("var_1", VariableType.INTEGER);
		c1 = new Constraint(var_1, ConstraintType.LESS, new Integer(5));
		c1 = new Constraint(var_2, ConstraintType.MORE, new Integer(-3));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_setup() {
		csp.addVariable(var_1, d1);
		csp.addVariable(var_2, d2);
		csp.addConstraint(c1);
		csp.addConstraint(c2);
		//no fuck-up = test passes
	}

}
