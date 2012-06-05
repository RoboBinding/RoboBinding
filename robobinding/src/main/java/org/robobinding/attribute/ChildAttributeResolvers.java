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
	private static final ChildAttributeResolvers INSTANCE = new ChildAttributeResolvers();
	
	private PropertyAttributeParser propertyAttributeParser;
	private PropertyAttributeResolver propertyAttributeResolver;
	private ValueModelAttributeResolver valueModelAttributeResolver;
	private StaticResourceAttributeResolver staticResourceAttributeResolver;
	private PredefinedMappingsAttributeResolver predefinedMappingsAttributeResolver;
	private PlainAttributeResolver plainAttributeResolver;
	
	private ChildAttributeResolvers()
	{
		propertyAttributeParser = new PropertyAttributeParser();
		
		propertyAttributeResolver = new PropertyAttributeResolver(propertyAttributeParser);
		valueModelAttributeResolver = new ValueModelAttributeResolver(propertyAttributeParser);
		staticResourceAttributeResolver = new StaticResourceAttributeResolver(propertyAttributeParser);
		predefinedMappingsAttributeResolver = new PredefinedMappingsAttributeResolver();
		plainAttributeResolver = new PlainAttributeResolver();
	}
	
	public static ChildAttributeResolver propertyAttributeResolver()
	{
		return INSTANCE.propertyAttributeResolver;
	}
	
	public static ChildAttributeResolver valueModelAttributeResolver()
	{
		return INSTANCE.valueModelAttributeResolver;
	}
	
	public static ChildAttributeResolver staticResourceAttributeResolver()
	{
		return INSTANCE.staticResourceAttributeResolver;
	}
	
	public static ChildAttributeResolver predefinedMappingsAttributeResolver()
	{
		return INSTANCE.predefinedMappingsAttributeResolver;
	}
	
	public static ChildAttributeResolver plainAttributeResolver()
	{
		return INSTANCE.plainAttributeResolver;
	}
	
	private static class PropertyAttributeResolver implements ChildAttributeResolver
	{
		private PropertyAttributeParser propertyAttributeParser;
		public PropertyAttributeResolver(PropertyAttributeParser propertyAttributeParser)
		{
			this.propertyAttributeParser = propertyAttributeParser;
		}
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return propertyAttributeParser.parse(attribute, attributeValue);
		}
	
	}

	private static class ValueModelAttributeResolver implements ChildAttributeResolver
	{
		private PropertyAttributeParser propertyAttributeParser;
		public ValueModelAttributeResolver(PropertyAttributeParser propertyAttributeParser)
		{
			this.propertyAttributeParser = propertyAttributeParser;
		}
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return propertyAttributeParser.parse(attribute, attributeValue);
		}

	}
	
	private static class StaticResourceAttributeResolver implements ChildAttributeResolver
	{
		private PropertyAttributeParser propertyAttributeParser;
		public StaticResourceAttributeResolver(PropertyAttributeParser propertyAttributeParser)
		{
			this.propertyAttributeParser = propertyAttributeParser;
		}
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return propertyAttributeParser.parseAsStaticResourceAttribute(attribute, attributeValue);
		}

	}
	
	private static class PredefinedMappingsAttributeResolver implements ChildAttributeResolver
	{
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return new PredefinedMappingsAttribute(attribute, attributeValue);
		}

	}
	
	private static class PlainAttributeResolver implements ChildAttributeResolver
	{
		
		@Override
		public AbstractAttribute resolveChildAttribute(String attribute, String attributeValue)
		{
			return new PlainAttribute(attribute, attributeValue);
		}

	}
}
