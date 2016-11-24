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
import com.mictale.jsonite.JsonBoolean;
import com.mictale.jsonite.JsonNull;
import com.mictale.jsonite.JsonNumber;
import com.mictale.jsonite.JsonObject;
import com.mictale.jsonite.JsonString;
import com.mictale.jsonite.JsonValue;

import java.util.Map;

/**
 * Produces a hierarchy of {@link JsonValue}s from a stream.
 * 
 * @author michael@mictale.com
 */
public final class JsonValueProducer implements Producer, JsonVisitor {

	private Consumer consumer;
	
	private JsonValue value;
	
	public JsonValueProducer(JsonValue value) {
		this.value = value;
	}
	
	@Override
	public void copyTo(Consumer consumer) {
		this.consumer = consumer;
		value.accept(this);
	}

	@Override
	public void visit(JsonObject obj) {
		consumer.append(new Event(EventType.START_OBJECT, obj, obj.getPosition()));
		
		for (Map.Entry<String, JsonValue> entry : obj.entrySet()) {
			JsonValue value = entry.getValue();
			consumer.append(new Event(EventType.MEMBER_NAME, JsonString.of(entry.getKey()), value.getPosition()));
			value.accept(this);
		}

		consumer.append(new Event(EventType.END_OBJECT, obj, obj.getPosition()));		
	}

	@Override
	public void visit(JsonArray arr) {
		consumer.append(new Event(EventType.START_ARRAY, arr, arr.getPosition()));
		
		for (JsonValue element : arr) {
			element.accept(this);
		}

		consumer.append(new Event(EventType.END_ARRAY, arr, arr.getPosition()));		
	}

	@Override
	public void visit(JsonString string) {
		consumer.append(new Event(EventType.PRIMITIVE, string, string.getPosition()));
	}

	@Override
	public void visit(JsonBoolean bool) {
		consumer.append(new Event(EventType.PRIMITIVE, bool, bool.getPosition()));
	}

	@Override
	public void visit(JsonNumber number) {
		consumer.append(new Event(EventType.PRIMITIVE, number, number.getPosition()));
	}

	@Override
	public void visit(JsonNull nul) {
		consumer.append(new Event(EventType.PRIMITIVE, nul, nul.getPosition()));
	}
}
