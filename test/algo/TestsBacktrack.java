package algo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import core.CSP;
import core.Constraint;
import core.Domain;
import core.IntegerDomain;
import core.Variable;
import core.enums.ConstraintType;
import core.enums.VariableType;

public class TestsBacktrack {
	
	private Backtrack bt;
	private CSP csp;
	
	/**
	 * Domain = d1
	 */
	private Variable var_1;
	/**
	 * Domain = d1
	 */
	private Variable var_2;
	/**
	 * Domain = d2
	 */
	private Variable var_3;
	
	private Domain d1;
	private Domain d2;
	
	private Constraint c_v1_inf_5;
	private Constraint c_v2_sup_v1;
	private Constraint c_v3_inf_7;
	
	@Before
	public void setUp() throws Exception {
		var_1 = new Variable("var_1", VariableType.INTEGER);
		var_2 = new Variable("var_2", VariableType.INTEGER);
		var_3 = new Variable("var_3", VariableType.INTEGER);
		
		d1 = new IntegerDomain(0, 15);
		d2 = new IntegerDomain(-8, 8);
		
		c_v1_inf_5 = new Constraint(var_1, ConstraintType.INF, 5);
		c_v2_sup_v1 = new Constraint(var_2, ConstraintType.SUP, var_1);
		c_v3_inf_7 = new Constraint(var_3, ConstraintType.INF, 7);
		
		csp = new CSP();
		csp.addVariable(var_1, d1);
		csp.addVariable(var_2, d1);
		csp.addVariable(var_3, d2);
		
		csp.addConstraint(c_v1_inf_5);
		csp.addConstraint(c_v2_sup_v1);
		csp.addConstraint(c_v3_inf_7);
		
		bt = new Backtrack(csp);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * For the record:
	 * <br />Variable [type=INTEGER, value=-8, name=var_3]
	 * <br />Variable [type=INTEGER, value=0, name=var_1]
	 * <br />Variable [type=INTEGER, value=1, name=var_2]
	 * @throws AlgorithmException
	 */
	@Test
	public void test() throws AlgorithmException {
		assertFalse(csp.isProblemSolved());
		boolean result = bt.backtrack(csp);
		assertTrue(result);
		assertEquals(-8, var_3.getValue());
		assertEquals(0, var_1.getValue());
		assertEquals(1, var_2.getValue());
	}
	
	@Ignore
	@Test
	public void test_start() throws AlgorithmException {
		bt.start();
		assertTrue(csp.isProblemSolved());
	}

}
