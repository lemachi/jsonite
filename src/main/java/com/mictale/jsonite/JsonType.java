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
package com.mictale.jsonite;

/**
 * The JSON type system.
 * <p>
 * The JSON type system knows primitive types ({@link #STRING}, {@link #NUMBER}, {@link #BOOLEAN} and {@link #NULL})
 * and composite types ({@link #OBJECT} and {@link #ARRAY}).
 *  
 * @author michael@mictale.com
 */
public enum JsonType {
	/**
	 * A JSON object, can reference other values using properties.
	 */
	OBJECT("Object"),
	
	/**
	 * A JSON array, can reference other values using members.
	 */
	ARRAY("Array"),
	
	/**
	 * A JSON string.
	 */
	STRING("String"),
	
	/**
	 * A JSON number.
	 */
	NUMBER("Number"),
	
	/**
	 * A JSON boolean, either <code>true</code> or <code>false</code>.
	 */
	BOOLEAN("Boolean"),
	
	/**
	 * The JSON <code>null</code> value.
	 */
	NULL("Null");
	
	private final String typeName;
	
	JsonType(String typeName) {
		this.typeName = typeName;
	}
	
	/**
	 * Retrieves the string representation of this type.
	 * 
	 * @return the type name.
	 */
	public String getTypeName() {
		return typeName;
	}
}
