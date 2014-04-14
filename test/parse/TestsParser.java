package parse;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.CSP;
import core.Constraint;
import core.Domain;
import core.Variable;
import parse.Parser;


public class TestsParser {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_removeComments() {
		String o1 = "o";
		String o2 = "o;";
		String o3 = "o;o";
		assertEquals(o1, Parser.removeComments(o1));
		assertEquals(o1, Parser.removeComments(o2));
		assertEquals(o1, Parser.removeComments(o3));
		String e1 = ";o";
		assertEquals("", Parser.removeComments(e1));
	}

	@Test
	public void test_getName() {
		String line = "$Name: test ";
		assertEquals("test", Parser.getName(line));
	}
	
	@Test
	public void test_parse() throws IOException {
		String filepath = "data/sample_csp.txt";
		CSP csp = Parser.parseCSP(filepath);
		for (Variable var : csp.getVariables().keySet()) {
			assertTrue(var.getName().equals("var_1")
					|| var.getName().equals("var_2")
					|| var.getName().equals("var_3"));
		}
		for (Domain dom : csp.getDomains()) {
			assertTrue(dom.getName().equals("d1")
					|| dom.getName().equals("d2"));
		}
		for (Constraint con : csp.getConstraints()) {
			assertTrue(con.getLeft().getName().equals("var_1")
					|| con.getLeft().getName().equals("var_2")
					|| con.getLeft().getName().equals("var_3"));
		}
	}
	
	@Test
	public void test_parse_big() throws IOException {
		String filepath = "data/big_csp.txt";
		Parser.parseCSP(filepath);
		// No fuck up = passes
	}
}
