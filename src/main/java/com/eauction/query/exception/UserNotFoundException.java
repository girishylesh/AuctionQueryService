package com.eauction.query.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3026527827027246156L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
