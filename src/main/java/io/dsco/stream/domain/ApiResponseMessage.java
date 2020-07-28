package io.dsco.stream.domain;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ApiResponseMessage {
	//ENUMS
	public enum SEVERITY {
		@SerializedName("info") INFO,
		@SerializedName("warn") WARN,
		@SerializedName("error") ERROR
	}

	//MEMBERS
	/* A code indicating what happened */
	private String code;
	/* The severity of what is being reported by this message */
	private SEVERITY severity;
	/* A description of the message */
	private String description;

	//CONSTRUCTORS
	public ApiResponseMessage() {}

	//ACCESSORS / MUTATORS
	public String getCode() { return code; }
	public void setCode(String val) { code = val; }
	public SEVERITY getSeverity() { return severity; }
	public void setSeverity(SEVERITY val) { severity = val; }
	public String getDescription() { return description; }
	public void setDescription(String val) { description = val; }
}