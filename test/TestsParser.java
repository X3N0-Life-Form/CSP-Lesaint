import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import core.CSP;
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
		System.out.println(csp);
	}
}
