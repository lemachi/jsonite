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

/**
 * Iterates over a JSON tree.
 * <p>
 * This interface provides support for the <a href="http://en.wikipedia.org/wiki/Visitor_pattern">visitor pattern</a>.
 * A client typically implements this interface and then calls {@link JsonValue#accept(JsonVisitor)} on
 * an instance of a JSON value which in turn calls the methods of the visitor for each element.
 * 
 * @see JsonValue#accept(JsonVisitor)
 * @author michael@mictale.com
 */
public interface JsonVisitor {

    /**
     * Called to consume a JSON object.
     *
     * @param obj is the object to consume.
     */
	void visit(JsonObject obj);

    /**
     * Called to consume a JSON array.
     *
     * @param arr is the array to consume.
     */
	void visit(JsonArray arr);

    /**
     * Called to consume a string.
     *
     * @param string is the string to consume.
     */
	void visit(JsonString string);

    /**
     * Called to consume a boolean value.
     *
     * @param bool is the value to consume.
     */
	void visit(JsonBoolean bool);

    /**
     * Called to consume a number value.
     *
     * @param number is the value to consume.
     */
	void visit(JsonNumber number);

    /**
     * Called to consume a null value.
     *
     * @param nul is the value to consume.
     */
	void visit(JsonNull nul);
}
