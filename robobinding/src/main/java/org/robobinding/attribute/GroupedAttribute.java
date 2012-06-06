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

import java.util.Map;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupedAttribute
{
	private GroupedAttributeDescriptor descriptor;
	private Map<String, AbstractAttribute> childAttributes;
	public GroupedAttribute(GroupedAttributeDescriptor descriptor, ChildAttributeResolverMapper resolverMapper)
	{
		this.descriptor = descriptor;
		
		ChildAttributeResolverMappings resolverMappings = createResolverMappings(resolverMapper);
		resolveChildAttributes(resolverMappings);
	}
	
	private ChildAttributeResolverMappings createResolverMappings(ChildAttributeResolverMapper resolverMapper)
	{
		ChildAttributeResolverMappings resolverMappings = new ChildAttributeResolverMappings();
		resolverMapper.mapChildAttributeResolvers(resolverMappings);
		return resolverMappings;
	}

	private void resolveChildAttributes(ChildAttributeResolverMappings resolverMappings)
	{
		childAttributes = Maps.newHashMap();
		for(Map.Entry<String, String> attributeEntry : descriptor.presentAttributes())
		{
			String attribute = attributeEntry.getKey();
			ChildAttributeResolver resolver = resolverMappings.resolverFor(attribute);
			AbstractAttribute childAttribute = resolver.resolveChildAttribute(attribute, attributeEntry.getValue());
			childAttributes.put(attribute, childAttribute);
		}
	}

	public boolean hasAttribute(String attributeName)
	{
		return childAttributes.containsKey(attributeName);
	}

	public CommandAttribute commandAttributeFor(String attributeName)
	{
		return attributeFor(attributeName);
	}

	public ValueModelAttribute valueModelAttributeFor(String attributeName)
	{
		return attributeFor(attributeName);
	}

	public StaticResourceAttribute staticResourceAttributeFor(String attributeName)
	{
		return attributeFor(attributeName);
	}
	
	public PlainAttribute plainAttributeFor(String attributeName)
	{
		return attributeFor(attributeName);
	}

	@SuppressWarnings("unchecked")
	public <AttributeType extends AbstractAttribute> AttributeType attributeFor(String attributeName)
	{
		return (AttributeType)childAttributes.get(attributeName);
	}
}
