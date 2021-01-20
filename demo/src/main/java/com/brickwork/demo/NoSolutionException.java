package com.brickwork.demo;

import java.lang.RuntimeException;

/**
 * Validity of user input should always be checked 
 * programatically. Here an exception wrapper is used
 * for a more convenient handling. 
 */
public class NoSolutionException extends RuntimeException {

	private static final String DEFAULT_MESSAGE = "-1. No solution exists. ";

	/*
	 * Version number for deserialization to verify that 
	 * that the sender and receiver of a serialized object 
	 * have loaded classes for that object that are 
	 * compatible with respect to serialization
	 */
	private static final long serialVersionUID = 1;

	public NoSolutionException() {

		super();
	}

	public NoSolutionException(String excMessage) {

		super(DEFAULT_MESSAGE + excMessage);
	}

	public NoSolutionException(String excMessage, Throwable cause) {

		super(DEFAULT_MESSAGE + excMessage, cause);
	}
}
