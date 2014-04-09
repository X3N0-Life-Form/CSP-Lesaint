package core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.enums.VariableType;
import core.exceptions.DomainException;
import core.exceptions.VariableException;

public class TestsIntegerDomain {
	/**
	 * range = 0..10
	 */
	private IntegerDomain domain;
	/**
	 * list = {1, 15}
	 */
	private IntegerDomain domain_with_list;
	
	private Variable var_in;
	private Variable var_out;
	private Variable var_other;
	private List<Integer> list;
	
	@Before
	public void setup() throws VariableException {
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(15);
		
		domain = new IntegerDomain(0, 10);
		domain_with_list = new IntegerDomain(list);
		
		var_in = new Variable("var_in", VariableType.INTEGER);
		var_in.setValue(new Integer(1));
		var_out = new Variable("var_out", VariableType.INTEGER);
		var_out.setValue(new Integer(99));
		var_other = new Variable("var_other", VariableType.INTEGER);
		var_other.setValue(new Integer(15));
	}
	
	@Test
	public void test_includes_OK() throws DomainException {
		assertTrue(domain.includes(var_in));
		assertFalse(domain.includes(var_out));
		assertTrue(domain_with_list.includes(var_in));
		assertFalse(domain_with_list.includes(var_out));
		assertTrue(domain_with_list.includes(var_other));
	}
	
	@Test(expected=DomainException.class)
	public void test_includes_KO() throws DomainException {
		domain.includes(new Variable("something", VariableType.TEST_UNSUPPORTED));
	}
	
	@Test
	public void test_forbiddenRange() {
		// not part of forbidden values
		assertFalse(domain.isValueForbidden(10));
		assertFalse(domain.isValueForbidden(1));
		assertFalse(domain.isValueForbidden(-1));
		assertFalse(domain.isValueForbidden(11));
		assertFalse(domain_with_list.isValueForbidden(1));
		assertFalse(domain_with_list.isValueForbidden(700));
		// explicitly forbidden
		domain.addForbiddenRange(2, 6);
		assertTrue(domain.isValueForbidden(2));
		assertTrue(domain.isValueForbidden(3));
		assertTrue(domain.isValueForbidden(6));
		assertFalse(domain.isValueForbidden(1));
		assertFalse(domain.isValueForbidden(9));
		domain.addForbiddenValue(9);
		assertTrue(domain.isValueForbidden(9));
	}
}
