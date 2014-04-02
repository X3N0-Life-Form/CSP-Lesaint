package core;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.enums.VariableType;
import core.exceptions.VariableException;

public class TestsVariable {
	private Variable var_uninitialized;
	private Variable var_2;
	private Variable var_2_bis;
	private Variable var_1;
	private Variable var_3;

	@Before
	public void setup() throws VariableException {
		var_uninitialized = new Variable("var_uninitialized", VariableType.INTEGER);
		
		var_2 = new Variable("var_2", VariableType.INTEGER);
		var_2_bis = new Variable("var_2_bis", VariableType.INTEGER);
		var_1 = new Variable("var_1", VariableType.INTEGER);
		var_3 = new Variable("var_3", VariableType.INTEGER);
		
		var_2.setValue(new Integer(2));
		var_2_bis.setValue(new Integer(2));
		var_1.setValue(new Integer(1));
		var_3.setValue(new Integer(3));
	}
	
	@Test
	public void test_setValue_validType() throws VariableException {
		Integer int_value = new Integer(5); 
		var_uninitialized.setValue(int_value);
		//no error = OK
	}
	
	@Test(expected=VariableException.class)
	public void test_setValue_null() throws VariableException {
		var_uninitialized.setValue(null);
	}
	
	@Test(expected=VariableException.class)
	public void test_setValue_invalidType() throws VariableException {
		var_uninitialized.setValue(new Object());
	}
	
	@Test
	public void test_compareTo() {
		assertTrue(var_2.compareTo(var_2_bis) == 0);
		assertTrue(var_2.compareTo(var_1) > 0);
		assertTrue(var_2.compareTo(var_3) < 0);
	}
	
	@Test
	public void test_compareToValue() throws VariableException {
		assertTrue(var_2.compareToValue(new Integer(2)) == 0);
		assertTrue(var_2.compareToValue(new Integer(1)) > 0);
		assertTrue(var_2.compareToValue(new Integer(3)) < 0);
	}
	
	@Test(expected=VariableException.class)
	public void test_compareToValue_null() throws VariableException {
		var_2.compareToValue(null);
	}
	
	@Test(expected=VariableException.class)
	public void test_compareToValue_invalidType() throws VariableException {
		var_2.compareToValue(new Object());
	}
}
