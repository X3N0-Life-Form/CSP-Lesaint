package core;

import core.exceptions.DomainException;

public abstract class Domain {
	public abstract boolean includes(Variable var) throws DomainException;
}
