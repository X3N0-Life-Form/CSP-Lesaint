package algo;

import java.util.LinkedList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import parse.Parser;
import core.CSP;

public class TestsBigOnes {
	private static List<String> results;
	private CSP big_csp;
	private Backtrack backtrack;
	private ArcConsistency_1 AC1;
	private Backtrack_2 bt2;
	
	@BeforeClass
	public static void setup_before() {
		results = new LinkedList<String>();
	}

	@Before
	public void setUp() throws Exception {
		big_csp = Parser.parseCSP("data/big_csp.txt");
	}

	@AfterClass
	public static void tearDown_after() throws Exception {
		printRecap();
	}

	private static void printRecap() {
		for (String current : results) {
			System.out.println(current);
		}
	}

	@Test
	public void test_big_BT() throws AlgorithmException {
		backtrack = new Backtrack(big_csp);
		backtrack.start();
		results.add("big_csp.txt: BT - " + backtrack.getRunTimeString());
	}
	
	@Test
	public void test_big_AC1_BT() throws AlgorithmException {
		backtrack = new Backtrack(big_csp);
		AC1 = new ArcConsistency_1(big_csp);
		backtrack.addBeforeAlgorithm(AC1);
		backtrack.start();
		results.add("big_csp.txt: AC1 + BT - " + backtrack.getRunTimeString()
				+ " ( + AC1 runtime " + AC1.getRunTimeString() + ")");
	}

	@Test
	public void test_big_BT2() throws AlgorithmException {
		bt2 = new Backtrack_2(big_csp);
		bt2.start();
		results.add("big_csp.txt: BT2 - " + bt2.getRunTimeString());
	}
	
	//@Test
	public void test_big_AC1_BT2() throws AlgorithmException {
		bt2 = new Backtrack_2(big_csp);
		AC1 = new ArcConsistency_1(big_csp);
		bt2.addBeforeAlgorithm(AC1);
		bt2.start();
		results.add("big_csp.txt: AC1 + BT2 - " + bt2.getRunTimeString()
				+ " ( + AC1 runtime " + AC1.getRunTimeString() + ")");
	}
}
