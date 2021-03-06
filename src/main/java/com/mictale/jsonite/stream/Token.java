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

import com.mictale.jsonite.JsonValue;
import com.mictale.jsonite.SourcePosition;

/**
 * A single element provided by a {@link Producer} and consumed by a
 * {@link Consumer}. A token is a combination of a {@link TokenType}
 * and a {@link JsonValue}.
 * 
 * @author michael@mictale.com
 */
public final class Token {
	
	private final TokenType tokenType;
	
	private final JsonValue value;

	private final SourcePosition position;

    public Token(TokenType nodeType, JsonValue value) {
        this(nodeType, value, null);
    }

	public Token(TokenType nodeType, JsonValue value, SourcePosition position) {
		this.tokenType = nodeType;
		this.value = value;
		this.position = position;
	}

	/**
	 * Returns the token type of this instance.
	 * 
	 * @return the token type.
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Returns the value of this token or <code>null</code> if
	 * the value is not defined.
	 * 
	 * @return the value of this token.
	 */
	public JsonValue getValue() {
		return value;
	}

	/**
	 * Retrieves the source position.
	 * 
	 * @return the source position.
	 */
	public SourcePosition getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		return tokenType.toString() + ":" + value;
	}
}
