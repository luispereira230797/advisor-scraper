package com.advisorscraper.advisors.infrastructure.exception;

import lombok.Getter;

@Getter
public class AdvisorValidationException extends RuntimeException {
	private final String code;
	private final Integer codeError;
	private static final long serialVersionUID = 1L;

	public AdvisorValidationException(Integer codeError, String code, String message) {
		super(message);
		this.code = code;
		this.codeError = codeError;
	}

}
