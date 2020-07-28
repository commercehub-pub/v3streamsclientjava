package io.dsco.stream.domain;


@SuppressWarnings("unused")
public class ChangeLogDiffChange {
	//MEMBERS
	/* The name of the attribute that changed */
	private String attribute;

	//CONSTRUCTORS
	public ChangeLogDiffChange() {}

	//ACCESSORS / MUTATORS
	public String getAttribute() { return attribute; }
	public void setAttribute(String val) { attribute = val; }
}