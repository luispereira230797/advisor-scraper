package com.newsscraper.news.infrastructure.exception;

import lombok.Getter;

@Getter
public class NewsValidationException extends RuntimeException {
	private final String code;
	private final Integer codeError;
	private static final long serialVersionUID = 1L;

	public NewsValidationException(Integer codeError, String code, String message) {
		super(message);
		this.code = code;
		this.codeError = codeError;
	}

}
