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
import java.io.Writer;

import com.mictale.jsonite.JsonArray;
import com.mictale.jsonite.JsonBoolean;
import com.mictale.jsonite.JsonNull;
import com.mictale.jsonite.JsonNumber;
import com.mictale.jsonite.JsonObject;
import com.mictale.jsonite.JsonString;

/**
 * Consumes {@link Token}s by writing them to a specified {@link Writer}.
 * 
 * @author michael@mictale.com
 */
public class JsonStreamConsumer implements Consumer, JsonVisitor {

	protected final Writer writer;

	/**
	 * Separator control.
	 */
	private boolean valueWritten;

	public JsonStreamConsumer(Writer writer) {
		this.writer = writer;
	}

	protected void newLine() throws IOException {
	}

    protected void space() throws IOException {
    }

	private void addIndent() throws IOException {
	}
	
	private void removeIndent() throws IOException {
	}

    private void appendSeparator() throws IOException {
        if (valueWritten) {
            writer.write(JsonSyntax.SEPARATOR);
            valueWritten = false;
            newLine();
        }
    }

    @Override
	public void append(Token token) throws BrokenStreamException {
		try {
			switch (token.getTokenType()) {
			case START_ARRAY:
				appendSeparator();
				writer.write(JsonSyntax.ARRAY_BEGIN);
				addIndent();
				break;

			case END_ARRAY:
				removeIndent();
				writer.write(JsonSyntax.ARRAY_END);
				valueWritten = true;
				break;

			case START_OBJECT:
				appendSeparator();
				writer.write(JsonSyntax.OBJECT_BEGIN);
				addIndent();
				break;

			case END_OBJECT:
				removeIndent();
				writer.write(JsonSyntax.OBJECT_END);
				valueWritten = true;
				break;

			case MEMBER_NAME:
				appendSeparator();
				token.getValue().accept(this);
				writer.write(JsonSyntax.OBJECT_MEMBER_SEPARATOR);
                space();
				break;

			case PRIMITIVE:
				appendSeparator();				
				token.getValue().accept(this);
				valueWritten = true;
				break;
				
			default:
				throw new AssertionError(token.getTokenType());
			}
		} catch (IOException e) {
			throw new BrokenStreamException(e);
		}
	}

	@Override
	public void visit(JsonObject obj) {
		assert false;
	}

	@Override
	public void visit(JsonArray arr) {
		assert false;
	}

	@Override
	public void visit(JsonString string) {
		String value = string.stringValue();

		try {
			writer.write(JsonSyntax.STRING_QUOTE);

			for (int i = 0; i < value.length(); i++) {
				char ch = value.charAt(i);
				switch (ch) {
				case JsonSyntax.STRING_QUOTE:
				case JsonSyntax.STRING_ESCAPE:
				case JsonSyntax.ESCAPE_SOLIDUS:
					writer.write(JsonSyntax.STRING_ESCAPE);
					writer.write(ch);
					break;
				case '\b':
					writer.write(JsonSyntax.STRING_ESCAPE);
					writer.write(JsonSyntax.ESCAPE_BACKSPACE);
					break;
				case '\f':
					writer.write(JsonSyntax.STRING_ESCAPE);
					writer.write(JsonSyntax.ESCAPE_FEED);
					break;
				case '\n':
					writer.write(JsonSyntax.STRING_ESCAPE);
					writer.write(JsonSyntax.ESCAPE_NEWLINE);
					break;
				case '\r':
					writer.write(JsonSyntax.STRING_ESCAPE);
					writer.write(JsonSyntax.ESCAPE_RETURN);
					break;
				case '\t':
					writer.write(JsonSyntax.STRING_ESCAPE);
					writer.write(JsonSyntax.ESCAPE_TAB);
					break;
				default:
					writer.write(ch);
					break;
				}
			}

			writer.write(JsonSyntax.STRING_QUOTE);
		} catch (IOException e) {
			throw new BrokenStreamException(e);
		}
	}

	@Override
	public void visit(JsonBoolean bool) {
		try {
			writer.write(bool.booleanValue() ? JsonSyntax.BOOLEAN_TRUE : JsonSyntax.BOOLEAN_FALSE);
		}
		catch(IOException e) {
			throw new BrokenStreamException(e);
		}
	}

	@Override
	public void visit(JsonNumber number) {
		try {
			writer.write(number.stringValue());
		}
		catch(IOException e) {
			throw new BrokenStreamException(e);
		}
	}

	@Override
	public void visit(JsonNull nul) {
		try {
			writer.write(JsonSyntax.NULL);
		}
		catch(IOException e) {
			throw new BrokenStreamException(e);
		}
	}
}
