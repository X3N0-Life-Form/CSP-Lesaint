package core;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.enums.ConstraintType;
import core.enums.VariableType;
import core.exceptions.VariableException;

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
		d2 = new IntegerDomain(-5, -1);
		var_1 = new Variable("var_1", VariableType.INTEGER);
		var_2 = new Variable("var_2", VariableType.INTEGER);
		c1 = new Constraint(var_1, ConstraintType.INF, new Integer(5));
		c2 = new Constraint(var_2, ConstraintType.SUP, new Integer(-3));
	}

	@After
	public void tearDown() throws Exception {
	}

	protected void setup_csp() {
		csp.addVariable(var_1, d1);
		csp.addVariable(var_2, d2);
		csp.addConstraint(c1);
		csp.addConstraint(c2);
	}
	
	@Test
	public void test_setup() {
		setup_csp();
		// no fuck-up = passes
		assertFalse(csp.getDomains().isEmpty());
		assertTrue(csp.getVariables().containsKey(var_1));
		assertTrue(csp.getVariables().containsKey(var_2));
		assertNotNull(csp.getVariables().get(var_1));
		assertNotNull(csp.getVariables().get(var_2));
		assertEquals(d1, csp.getVariables().get(var_1));
		assertEquals(d2, csp.getVariables().get(var_2));
	}
	
	@Test
	public void test_problemSolved() throws VariableException {
		setup_csp();
		assertFalse(csp.isProblemSolved());
		var_1.setValue(new Integer(1));
		var_2.setValue(new Integer(-1));
		assertTrue(csp.isProblemSolved());
	}

	@Test
	public void test_problemSolved_domainFuckUp() throws VariableException {
		setup_csp();
		var_1.setValue(new Integer(1));
		var_2.setValue(new Integer(50));
		assertFalse(csp.isProblemSolved());
	}

	@Test
	public void test_getConstraints() {
		setup_csp();
		List<Constraint> list_1 = csp.getConstraints(var_1);
		List<Constraint> list_2 = csp.getConstraints(var_2);
		assertTrue(list_1.size() > 0);
		assertTrue(list_2.size() > 0);
		
		assertTrue(list_1.contains(c1));
		assertFalse(list_1.contains(c2));
		
		assertTrue(list_2.contains(c2));
		assertFalse(list_2.contains(c1));
	}
}
