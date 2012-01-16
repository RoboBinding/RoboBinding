/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.property;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Bean
{
	public static final String READ_ONLY_PROPERTY = "readOnlyProperty";
	public static final String WRITE_ONLY_PROPERTY = "writeOnlyProperty";
	public static final String ANNOTATED_PROPERTY = "annotatedProperty";
	public static final String NOT_ANNOTATED_PROPERTY = "notAnnotatedProperty";
	public static final String PROPERTY = "property";
	public static final String READ_WRITE_PROPERTY = "readWriteProperty";
	
	public boolean writeOnlyPropertyValue = true;
	public boolean getReadOnlyProperty()
	{
		return true;
	}
	public void setWriteOnlyProperty(boolean newValue)
	{
		writeOnlyPropertyValue = newValue;
	}
	@PropertyAnnotation
	public boolean getAnnotatedProperty()
	{
		return true;
	}
	public boolean getNotAnnotatedProperty()
	{
		return true;
	}
	public boolean getProperty()
	{
		return true;
	}
	public boolean getReadWriteProperty()
	{
		return true;
	}
	public void setReadWriteProperty(boolean b)
	{
	}
}
