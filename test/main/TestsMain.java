package main;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import algo.AlgorithmException;

public class TestsMain {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_standard() throws AlgorithmException, IOException {
		String[] args = new String[] {
				"-file",
				"data/sample_csp.txt"
		};
		Main.main(args);
	}
	
	@Ignore
	@Test
	public void test_ac1() throws AlgorithmException, IOException {
		String[] args = new String[] {
				"-file",
				"data/sample_csp.txt",
				"-ac1"
		};
		Main.main(args);
	}

}
