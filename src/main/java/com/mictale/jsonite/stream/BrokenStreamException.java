package com.mictale.jsonite.stream;

import com.mictale.jsonite.JsonException;

/**
 * Thrown when a stream contains illegal data.
 *
 * @author michael@mictale.com
 */
public final class BrokenStreamException extends JsonException {

	private static final long serialVersionUID = -8499395581017262026L;

	public BrokenStreamException(String message) {
		super(message);
	}

	public BrokenStreamException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BrokenStreamException(Throwable cause) {
		super(cause);
	}
}
