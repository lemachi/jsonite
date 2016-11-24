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

/**
 * Contains all symbols that have a special meaning in JSON.
 * 
 * @author michael@mictale.com
 */
interface JsonSyntax {

	char ARRAY_BEGIN = '[';
	
	char ARRAY_END = ']';

	char OBJECT_MEMBER_SEPARATOR = ':';
	
	char SEPARATOR = ',';
	
	char OBJECT_END = '}';
	
	char OBJECT_BEGIN = '{';

	String NULL = "null";

	String BOOLEAN_FALSE = "false";

	String BOOLEAN_TRUE = "true";

	char STRING_QUOTE = '\"';

	char STRING_ESCAPE = '\\';

	char ESCAPE_SOLIDUS = '/';
	
	char ESCAPE_NEWLINE = 'n';

	char ESCAPE_FEED = 'f';

	char ESCAPE_TAB = 't';

	char ESCAPE_RETURN = 'r';
	
	char ESCAPE_BACKSPACE = 'b';

	char NUMBER_ENGINEERING_UPPER = 'E';

	char NUMBER_ENGINEERING = 'e';

	char NUMBER_SIGN = '-';

	char NUMBER_DECIMAL = '.';
}
