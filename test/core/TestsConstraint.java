package core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.enums.ConstraintType;
import core.enums.VariableType;
import core.exceptions.VariableException;

public class TestsConstraint {
	private Constraint c_v1_diff_1;
	private Constraint c_v1_diff_2;
	private Variable var_1;
	
	private Constraint c_v1_equal_1;
	private Constraint c_v1_less_1;
	private Constraint c_v1_less_equal_1;
	private Constraint c_v1_more_1;
	private Constraint c_v1_more_equal_1;
	private Variable var_unsupported;

	@Before
	public void setUp() throws Exception {
		var_unsupported = new Variable("var_unsupported", VariableType.TEST_UNSUPPORTED);
		var_1 = new Variable("var_1", VariableType.INTEGER);
		var_1.setValue(new Integer(1));
		c_v1_diff_1 = new Constraint(var_1, ConstraintType.DIFFERENT, new Integer(1));
		c_v1_diff_2 = new Constraint(var_1, ConstraintType.DIFFERENT, new Integer(2));
		
		c_v1_equal_1 = new Constraint(var_1, ConstraintType.EQUAL, new Integer(1));
		c_v1_less_1 = new Constraint(var_1, ConstraintType.INF, new Integer(1));
		c_v1_less_equal_1 = new Constraint(var_1, ConstraintType.INF_EQUAL, new Integer(1));
		c_v1_more_1 = new Constraint(var_1, ConstraintType.SUP, new Integer(1));
		c_v1_more_equal_1 = new Constraint(var_1, ConstraintType.SUP_EQUAL, new Integer(1));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_constraintValidation_basic() throws VariableException {
		assertFalse(c_v1_diff_1.isConstraintValid());
		assertTrue(c_v1_diff_2.isConstraintValid());
		var_1.setValue(new Integer(2));
		assertTrue(c_v1_diff_1.isConstraintValid());
	}
	
	@Test
	public void test_constraintValidation_exhaustive() throws VariableException {
		assertTrue(c_v1_equal_1.isConstraintValid());
		assertFalse(c_v1_less_1.isConstraintValid());
		assertTrue(c_v1_less_equal_1.isConstraintValid());
		assertFalse(c_v1_more_1.isConstraintValid());
		assertTrue(c_v1_more_equal_1.isConstraintValid());
	}
	
	@Test
	public void test_constraintValidation_unsetVar() throws VariableException {
		var_1 = new Variable("var_1", VariableType.INTEGER);
		Constraint c1 = new Constraint(var_1, ConstraintType.INF, new Integer(5));
		assertFalse(c1.isConstraintValid());
	}
	
	@Test(expected=VariableException.class)
	public void test_constructInvalidValueType() throws VariableException {
		new Constraint(var_1, ConstraintType.DIFFERENT, new Object());
	}
	
	@Test(expected=VariableException.class)
	public void test_constructInvalidVariableType() throws VariableException {
		new Constraint(var_1, ConstraintType.DIFFERENT, var_unsupported);
	}
	
}
