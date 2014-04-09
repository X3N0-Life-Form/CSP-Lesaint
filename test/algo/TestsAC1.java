package algo;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import core.CSP;
import core.Constraint;
import core.IntegerDomain;
import core.Variable;
import core.arcs.Arc;
import core.enums.ConstraintType;
import core.enums.VariableType;
import core.exceptions.DomainException;

public class TestsAC1 {

	private ArcConsistency_1 AC1;
	
	private CSP csp;
	private Variable var_1;
	private Variable var_2;
	private Variable var_3;
	/**
	 * var_1 & var_2; 0..15
	 */
	private IntegerDomain d1;
	/**
	 * var_3; {1, 15}
	 */
	private IntegerDomain d2;
	private Constraint c_v1_inf_5;
	private Constraint c_v2_sup_v1;
	private Constraint c_v3_inf_7;
	
	@Before
	public void setUp() throws Exception {
		csp = new CSP();
		// copied from TestsBacktrack
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
		// copy ends
		AC1 = new ArcConsistency_1(csp);
		
	}
	
	@Test
	public void test_construction() {
		Arc arc = new Arc(var_1, var_2);
		Set<Arc> arcs = AC1.getArcs();
		assertTrue(arcs.contains(arc));
	}
	
	@Test
	public void test_verifyConstraint() {
		assertTrue(AC1.verifyConstraint(c_v2_sup_v1, d1, d1, 0));
		//TODO: false
		// also need to test other constraint types
	}
	
	@Test
	public void test_verifyIntegerDomains() throws DomainException {
		assertTrue(AC1.verifyIntegerDomains(c_v2_sup_v1, d1, d1));
		//TODO: test on something that should return false;
	}
	
	/**
	 * Tests flagForForbiddation & updateForbiddenValues.
	 */
	@Test
	public void test_forbid_values() {
		assertFalse(d1.isValueForbidden(4));
		assertFalse(AC1.getToForbid().get(d1).contains(4));
		AC1.flagForForbiddation(4, d1);
		assertTrue(AC1.getToForbid().get(d1).contains(4));
		AC1.updateForbiddenValues();
		assertFalse(AC1.getToForbid().get(d1).contains(4));
		assertTrue(d1.isValueForbidden(4));
	}
	
	@Test
	public void test_checkArc() throws DomainException {
		Arc arc = new Arc(var_1, var_2);
		assertTrue(AC1.checkArc(arc));
		assertTrue(AC1.getToForbid().get(d1).contains(0));
	}
	
	@Test
	public void test_AC1() throws DomainException {
		assertTrue(d1.includes(0));
		AC1.AC1(csp);
		assertFalse(d1.includes(0));
	}
}
