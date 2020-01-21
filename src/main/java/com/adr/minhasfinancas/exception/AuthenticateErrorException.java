package com.adr.minhasfinancas.exception;

public class AuthenticateErrorException extends RuntimeException{
	public AuthenticateErrorException(String message) {
		super(message);
	}
}
