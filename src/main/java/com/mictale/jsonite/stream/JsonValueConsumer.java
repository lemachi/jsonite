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

import com.mictale.jsonite.JsonArray;
import com.mictale.jsonite.JsonException;
import com.mictale.jsonite.JsonObject;
import com.mictale.jsonite.JsonValue;

import java.util.Stack;

/**
 * Consumes a {@link JsonValue} and all its children.
 * 
 * @author michael@mictale.com
 */
public final class JsonValueConsumer implements Consumer {

	private JsonValue name;

	private JsonValue last;
	
	private Stack<JsonValue> path = new Stack<>();
	
	private void appendValue(JsonValue value) {
		if (!path.isEmpty()) {
			JsonValue context = path.peek();
			if (context.isArray()) {
				context.asArray().add(value);
			}
			else if (context.isObject()) {
                if (name == null) {
                    // Attempt to consume something like {"foo"}
                    throw new BrokenStreamException("Illegal object assignment");
                }
				context.asObject().put(name.stringValue(), value);
				name = null;
			}			
		}
		
		if (!value.isPrimitive()) {
			path.push(value);
		}		
	}

	@Override
	public void append(Event tuple) {
		switch(tuple.getEventType()) {
		case START_OBJECT:
			appendValue(last = new JsonObject());
			break;
		case START_ARRAY:
			appendValue(last = new JsonArray());
			break;
		case END_OBJECT:
		case END_ARRAY:
			last = path.pop();
			break;
		case MEMBER_NAME:
			name = tuple.getValue();
			break;
		case PRIMITIVE:
			last = tuple.getValue();
			appendValue(last);
			break;
		default:
			throw new JsonException("Unsupported node type: " + tuple.getEventType());
		}
	}

    /**
     * Retrieves the value produced by this instance.
     *
     * The instance has to be at a consistent state or invoking this method will throw
     * an {@link IllegalStateException}
     *
     * @return the value that has been produced.
     * @throws IllegalStateException when consuming the stream is incomplete.
     */
	public JsonValue getValue() {
		if (path.size() != 0) {
			throw new IllegalStateException("The token stream is incomplete");
		}
		return last;
	}
}