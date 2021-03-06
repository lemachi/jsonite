/* Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package com.mictale.jsonite.stream;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.mictale.jsonite.JsonNumber;
import com.mictale.jsonite.JsonValue;
import com.mictale.jsonite.SourcePosition;

/**
 * Produces JSON nodes from a character stream.
 * 
 * @author michael@mictale.com
 * 
 */
public final class JsonStreamProducer implements Producer {

	private static final int EOS = -1;

	private final Reader reader;

	private int line;
	
	private int column;
	
	private int position;
	
	private Consumer consumer;

	private TokenType lastNodeType = TokenType.UNDEFINED;

	private int lastChar;

	private final StringBuilder buffer = new StringBuilder();

	public JsonStreamProducer(Reader reader) {
		this.reader = reader;
	}

	public JsonStreamProducer(String json) {
		this(new StringReader(json));
	}

	private void throwUnexpected(String expected, String found) {
		throw new BrokenStreamException("Expected " + expected + " found " + found + " at line " + line + ", position " + column);
	}
	
	private void append(TokenType nodeType, JsonValue value) {
		SourcePosition p = new SourcePosition(position, line, column);
		Token event = new Token(nodeType, value, p);
		consumer.append(event);
		this.lastNodeType = nodeType;
	}

	private void eat(String expected) throws IOException {
		for (int i = 0; i < expected.length(); i++) {
			if (lastChar != expected.charAt(i)) {
				throwUnexpected(expected, String.valueOf((char)lastChar));
			}

			skip();
		}
	}

	private void nextPosition() {
		if (lastChar == '\n') {
			line++;
			column = 0;
		}
		else {
			column++;					
		}
		
		position++;
	}
	
	private int skipWs() throws IOException {
		if (lastChar != EOS) {
			do {
				lastChar = reader.read();
				nextPosition();
			} while (lastChar == ' ' || lastChar == '\r' || lastChar == '\n' || lastChar == '\t');
		}

		return lastChar;
	}

	private int skip() throws IOException {
		if (lastChar != EOS) {
			lastChar = reader.read();
			nextPosition();
		}

		return lastChar;
	}

	private String parseString() throws IOException {
		buffer.setLength(0);

		while (true) {
			skip();

			switch (lastChar) {
			default:
				buffer.append((char) lastChar);
				break;
			case JsonSyntax.STRING_QUOTE:
				skipWs();
				return buffer.toString();
			case JsonSyntax.STRING_ESCAPE:
				skipWs();
				switch (lastChar) {
				case JsonSyntax.STRING_QUOTE:
					buffer.append(JsonSyntax.STRING_QUOTE);
					break;
				case JsonSyntax.STRING_ESCAPE:
					buffer.append(JsonSyntax.STRING_ESCAPE);
					break;
				case JsonSyntax.ESCAPE_SOLIDUS:
					buffer.append(JsonSyntax.ESCAPE_SOLIDUS);
					break;
				case JsonSyntax.ESCAPE_BACKSPACE:
					buffer.append('\b');
					break;
				case JsonSyntax.ESCAPE_FEED:
					buffer.append('\f');
					break;
				case JsonSyntax.ESCAPE_NEWLINE:
					buffer.append('\n');
					break;
				case JsonSyntax.ESCAPE_RETURN:
					buffer.append('\r');
					break;
				case 't':
					buffer.append('\t');
					break;
				case 'u':
					byte[] data = new byte[] {
							(byte)(Character.digit((char) skip(), 16) << 4 | Character.digit((char) skip(), 16)),
							(byte)(Character.digit((char) skip(), 16) << 4 | Character.digit((char) skip(), 16)) };
										
					buffer.append(new String(data, "UNICODE"));
					break;
				}
			}
		}
	}

	private void parseTrue() throws IOException {
		eat(JsonSyntax.BOOLEAN_TRUE);
		append(TokenType.PRIMITIVE, JsonValue.TRUE);
	}

	private void parseFalse() throws IOException {
		eat(JsonSyntax.BOOLEAN_FALSE);
		append(TokenType.PRIMITIVE, JsonValue.FALSE);
	}

	private void parseNull() throws IOException {
		eat(JsonSyntax.NULL);
		append(TokenType.PRIMITIVE, JsonValue.NULL);
	}

	/**
	 * Parses a number and appends it.
	 */
	private void parseNumber() throws IOException {
		buffer.setLength(0);

		if (lastChar == JsonSyntax.NUMBER_SIGN) {
			buffer.append((char) lastChar);
			skip();
		}

		if (!Character.isDigit((char) lastChar)) {
			if ('N' == (char)lastChar) {
				eat("NaN");
				append(TokenType.PRIMITIVE, JsonNumber.of(Double.NaN));
			}
		}
		else {
			do {
				buffer.append((char) lastChar);
				skip();
			} while (Character.isDigit((char) lastChar));
	
			boolean isReal = false;
	
			if (lastChar == JsonSyntax.NUMBER_DECIMAL) {
				isReal = true;
				buffer.append((char) lastChar);
				skip();
	
				while (Character.isDigit((char) lastChar)) {
					buffer.append((char) lastChar);
					skip();
				}
			}
	
			if (lastChar == JsonSyntax.NUMBER_ENGINEERING || lastChar == JsonSyntax.NUMBER_ENGINEERING_UPPER) {
				isReal = true;
				buffer.append((char) lastChar);
				skip();
	
				if (lastChar == JsonSyntax.NUMBER_SIGN) {
					buffer.append((char) lastChar);
					skip();
				}
	
				while (Character.isDigit((char) lastChar)) {
					buffer.append((char) lastChar);
					skip();
				}
			}
	
			if (isReal) {
				append(TokenType.PRIMITIVE, JsonNumber.of(Double.parseDouble(buffer.toString())));
			} else {
				Long l = Long.parseLong(buffer.toString());
				append(TokenType.PRIMITIVE, JsonNumber.of(l));
			}
		}
	}

	private boolean parseAny() throws IOException {
		end: while (true) {
			switch (lastChar) {
			case -1:
				return false;
			case JsonSyntax.SEPARATOR:
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				skipWs();
				break;
			case JsonSyntax.OBJECT_BEGIN:
				append(TokenType.START_OBJECT, null);
				break end;
			case JsonSyntax.OBJECT_END:
				append(TokenType.END_OBJECT, null);
				break end;
			case JsonSyntax.ARRAY_BEGIN:
				append(TokenType.START_ARRAY, null);
				break end;
			case JsonSyntax.ARRAY_END:
				append(TokenType.END_ARRAY, null);
				break end;
			case JsonSyntax.STRING_QUOTE:
				String str = parseString();
				if (lastChar == JsonSyntax.OBJECT_MEMBER_SEPARATOR) {
					append(TokenType.MEMBER_NAME, JsonValue.of(str));
				} else {
					append(TokenType.PRIMITIVE, JsonValue.of(str));
				}
				break end;
			case 't':
				parseTrue();
				break end;
			case 'f':
				parseFalse();
				break end;
			case 'n':
				parseNull();
				break end;
			default:
				parseNumber();
				break end;
			}
		}

		return true;
	}

	@Override
	public void copyTo(Consumer consumer) {
		this.consumer = consumer;

		boolean running = true;

		try {
			skipWs();
			running = parseAny();
			
			while (running) {
				switch (lastNodeType) {
				case START_OBJECT:
					skipWs();
					running = parseAny();
					break;
				case MEMBER_NAME:
					if (lastChar != JsonSyntax.OBJECT_MEMBER_SEPARATOR) {
						throwUnexpected("':'", String.valueOf((char)lastChar));
					}
					skipWs();
					running = parseAny();
					break;
				case END_OBJECT:
					skipWs();
					running = parseAny();
					break;
				case START_ARRAY:
					skipWs();
					switch (lastChar) {
					case JsonSyntax.ARRAY_END:
						append(TokenType.END_ARRAY, null);
						break;
					default:
						running = parseAny();
						break;
					}
					break;
				case END_ARRAY:
					skipWs();
					running = parseAny();
					break;
				case PRIMITIVE:
					running = parseAny();
					break;
				case UNDEFINED:
					running = false;
					break;
				default:
					throw new AssertionError();
				}
			}
		} catch (IOException e) {
			throw new BrokenStreamException("Failed to parse string", e);
		}
	}
}
