/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingProperties
{
	public static final String PROPERTY_NAME = "propertyName";
	public static final String ONE_WAY_BINDING_PROPERTY_NAME = "{"+PROPERTY_NAME+"}";
	public static final String TWO_WAY_BINDING_PROPERTY_NAME = "$" + ONE_WAY_BINDING_PROPERTY_NAME;

	public static final String PROPERTY_NAME1 = "propertyName1";
	public static final String ONE_WAY_BINDING_PROPERTY_NAME1 = "{"+PROPERTY_NAME1+"}";
	public static final String TWO_WAY_BINDING_PROPERTY_NAME1 = "$" + ONE_WAY_BINDING_PROPERTY_NAME1;
	
	public static final String PROPERTY_NAME2 = "propertyName2";
	public static final String ONE_WAY_BINDING_PROPERTY_NAME2 = "{"+PROPERTY_NAME2+"}";
	public static final String TWO_WAY_BINDING_PROPERTY_NAME2 = "$" + ONE_WAY_BINDING_PROPERTY_NAME2;
}
