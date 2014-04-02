package core;

public abstract class Domain {
	public abstract boolean includes(Variable var) throws DomainException;
}
