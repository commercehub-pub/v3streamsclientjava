package io.dsco.stream.domain;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ChangeLogDiff {
	//ENUMS
	public enum STATE {
		@SerializedName("new") NEW,
		@SerializedName("changed") CHANGED,
		@SerializedName("deleted") DELETED
	}

	//MEMBERS
	/* This explains what change was made, one of the following values...
* **new**: indicates that this is a new object
* **changed**: indicates that an existing object was changed and the `changed` attribute will explain the change
* **deleted**: indicates that this object was deleted from Dsco (rare if ever happens) */
	private STATE state;
	private List<ChangeLogDiffChange> changed;

	//CONSTRUCTORS
	public ChangeLogDiff() {}

	//ACCESSORS / MUTATORS
	public STATE getState() { return state; }
	public void setState(STATE val) { state = val; }
	public List<ChangeLogDiffChange> getChanged() { return changed; }
	public void setChanged(List<ChangeLogDiffChange> val) { changed = val; }
}