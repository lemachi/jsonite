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

/**
 * Produces {@link Event}s.
 * <p>
 * A JSON producer generates a series of {@link Event}s. These {@link Event}s represent a logical
 * JSON stream where every element in the stream consists of a {@link EventType} and a {@link JsonValue}.
 *
 * Implementations of this interface are not checking the validity of a specific event sequence.
 * For example, a {@link EventType#START_ARRAY} could be immediately followed by a
 * {@link EventType#END_OBJECT}. It is up to the {@link Consumer} to detect these errors.
 *
 * @author michael@mictale.com
 */
public interface Producer {

    /**
     * Called to consume this producer by the specified consumer.
     * @param consumer is the consumer that should consume this producer.
     */
	void copyTo(Consumer consumer);
}
