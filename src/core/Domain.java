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

	protected String name = "";
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
