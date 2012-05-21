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
package org.robobinding.attribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildAttributeResolvers
{
	private ChildAttributeResolvers()
	{
	}
	
	public static ChildAttributeResolver propertyAttributeResolver()
	{
		return new PropertyAttributeResolver();
	}
	
	public static ChildAttributeResolver valueModelAttributeResolver()
	{
		return new ValueModelAttributeResolver();
	}
	
	public static ChildAttributeResolver staticResourceAttributeResolver()
	{
		return new StaticResourceAttributeResolver();
	}
	
	public static ChildAttributeResolver plainAttributeResolver()
	{
		return new PlainAttributeResolver();
	}
	
	static class ValueModelAttributeResolver implements ChildAttributeResolver
	{
		private PropertyAttributeParser propertyAttributeParser;
		public ValueModelAttributeResolver()
		{
			propertyAttributeParser = new PropertyAttributeParser();
		}
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return propertyAttributeParser.parse(attribute, attributeValue);
		}

	}
	
	static class StaticResourceAttributeResolver implements ChildAttributeResolver
	{
		private PropertyAttributeParser propertyAttributeParser;
		public StaticResourceAttributeResolver()
		{
			propertyAttributeParser = new PropertyAttributeParser();
		}
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return propertyAttributeParser.parseAsStaticResourceAttribute(attribute, attributeValue);
		}

	}
	
	static class PropertyAttributeResolver implements ChildAttributeResolver
	{
		private PropertyAttributeParser propertyAttributeParser;
		public PropertyAttributeResolver()
		{
			propertyAttributeParser = new PropertyAttributeParser();
		}
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return propertyAttributeParser.parse(attribute, attributeValue);
		}

	}

	static class PlainAttributeResolver implements ChildAttributeResolver
	{
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return new PlainAttribute(attribute, attributeValue);
		}

	}
}
