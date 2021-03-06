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
 * Implemented by classes that can be represented by a {@link JsonValue}.
 * <p>
 * Implementing classes must return enough state to fully reconstruct 
 * themselves from the returned JSON. They must also provide a way
 * to perform the reconstruction, e.g. by implementing a static method
 * that takes a JSON value. 
 * 
 * @author michael@mictale.com
 */
public interface HasJson {

	/**
	 * Retrieves the JSON representation of this instance.
	 * 
	 * @return the JSON representation.
	 */
	JsonValue asJson();
}
