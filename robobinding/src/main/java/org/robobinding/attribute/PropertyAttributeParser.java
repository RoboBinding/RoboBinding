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
package org.robobinding.attribute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PropertyAttributeParser
{
	public AbstractPropertyAttribute parse(String name, String value)
	{
		if (ValueModelAttribute.is(value))
		{
			return new ValueModelAttribute(name, value);
		} else if(StaticResourceAttribute.is(value))
		{
			return new StaticResourceAttribute(name, value);
		}
		
		throw new MalformedAttributeException(name, describeSyntaxError(value));
	}
	
	public ValueModelAttribute parseAsValueModelAttribute(String name, String value)
	{
		if(ValueModelAttribute.is(value))
		{
			return new ValueModelAttribute(name, value);
		}
		
		throw new MalformedAttributeException(name, describeSyntaxError(value));
	}
	
	StaticResourceAttribute parseAsStaticResourceAttribute(String name, String value)
	{
		if(StaticResourceAttribute.is(value))
		{
			return new StaticResourceAttribute(name, value);
		}
		
		throw new MalformedAttributeException(name, describeSyntaxError(value));
	}

	private String describeSyntaxError(String value)
	{
		String errorMessage = "Invalid binding syntax: '" + value + "'.";
			
		Matcher propertyBindingAttempted = Pattern.compile("\\{.+|.+\\}").matcher(value);
		Matcher resourceBindingAttempted = Pattern.compile(".*@.*").matcher(value);
		
		if (!propertyBindingAttempted.matches())
			errorMessage += "\n\nDid you mean '{" + value + "}' or '${" + value + "}'? (one/two-way binding)\n";
		else if (resourceBindingAttempted.matches())
			errorMessage += "\n\nDid you mean to omit the curly braces? (Curly braces are for binding to a property on the presentation model, not static resources)";
		
		return errorMessage;
	}
}
