package com.example.bfi.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoIdFieldException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3614755055534439176L;

	public NoIdFieldException(String msg) {
		 super(msg);
	 }
}
