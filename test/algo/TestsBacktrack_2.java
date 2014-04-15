package algo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parse.Parser;
import core.CSP;
import core.Variable;
import core.exceptions.VariableException;

public class TestsBacktrack_2 {
	
	private CSP csp;
	private Backtrack_2 bt2;
	private Variable var_1;
	private Variable var_2;
	private Variable var_3;

	@Before
	public void setUp() throws Exception {
		csp = Parser.parseCSP("data/sample_csp.txt");
		bt2 = new Backtrack_2(csp);
		var_1 = csp.getVariable("var_1");
		var_2 = csp.getVariable("var_2");
		var_3 = csp.getVariable("var_3");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_constraintsMightGetValidated() throws VariableException {
		var_1.setValue(6);
		assertFalse(bt2.constraintsMightGetValidated(csp, var_1));
		var_3.setValue(3);
		assertTrue(bt2.constraintsMightGetValidated(csp, var_3));
		var_1.setValue(4);
		var_2.setValue(1);
		assertTrue(bt2.constraintsMightGetValidated(csp, var_2));
	}

	@Test
	public void test_hjvo() throws AlgorithmException {
		assertTrue(bt2.backtrack(csp));
	}
}
