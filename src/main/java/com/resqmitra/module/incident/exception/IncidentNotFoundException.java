package com.resqmitra.module.incident.exception;

public class IncidentNotFoundException extends Exception {

	private static final long serialVersionUID = -6611044919122772259L;
	
	public IncidentNotFoundException() {
		super("User not found");
	}

	public IncidentNotFoundException(String string) {
		super(string);
	}

}
