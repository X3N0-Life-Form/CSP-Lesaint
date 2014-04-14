package algo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.CSP;
import core.Constraint;
import core.IntegerDomain;
import core.Variable;
import core.enums.ConstraintType;
import core.enums.VariableType;

public class TestsAlgorithm {
	
	private CSP csp;
	private Backtrack backtrack;
	private ArcConsistency_1 AC1;
	
	// the magic of copy/pasta
	/**
	 * d1
	 */
	private Variable var_1;
	/**
	 * d1
	 */
	private Variable var_2;
	/**
	 * d2
	 */
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
		
		backtrack = new Backtrack(csp);
		AC1 = new ArcConsistency_1(csp);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_anonymous_algo() throws AlgorithmException {
		class DummyMaster extends Algorithm {
			public DummyMaster(CSP csp) { super(csp); }
			public boolean gotExecd = false;
			@Override public String getName() {
				gotExecd = true;
				return "master";
			}
		}
		class DummyA extends DummyMaster {
			public DummyA(CSP csp) { super(csp); }
			@Override public String getName() {
				super.getName();
				return "sub";
			}
		}
		
		DummyMaster master = new DummyMaster(csp);
		DummyA a_1 = new DummyA(csp);
		DummyA a_2 = new DummyA(csp);
		DummyA a_3 = new DummyA(csp);
		master.addBeforeAlgorithm(a_1);
		master.addBeforeAlgorithm(a_2);
		master.start();
		assertTrue(master.gotExecd);
		assertTrue(a_1.gotExecd);
		assertTrue(a_2.gotExecd);
		assertFalse(a_3.gotExecd);
	}
	
	@Test
	public void test_proofThatThisThingIsSolvable() throws AlgorithmException {
		backtrack.start();
		assertTrue(backtrack.problem.isProblemSolved());
	}
	
	@Test
	public void test_AC1_Backtrack() throws AlgorithmException {
		System.out.println("############## BEGIN #################");
		backtrack.addBeforeAlgorithm(AC1);
		backtrack.start();
		System.out.println("############### END ####################");
		assertTrue(backtrack.isSolved());
	}

}
