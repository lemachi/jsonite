package com.mictale.jsonite;

/**
 * Thrown when a JSON value cannot be converted.
 */
public class JsonConversionException extends JsonException {

	private static final long serialVersionUID = -3441857198530657260L;

	JsonConversionException(String message) {
		super(message);
	}	
}
