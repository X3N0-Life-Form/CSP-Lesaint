package core;

import core.exceptions.DomainException;

public abstract class Domain {
	/**
	 * 
	 * @param var
	 * @return
	 * @throws DomainException Invalid variable type for this domain.
	 */
	public abstract boolean includes(Variable var) throws DomainException;
	
	/**
	 * 
	 * @return Size of the domain.
	 */
	public abstract int size();
}
