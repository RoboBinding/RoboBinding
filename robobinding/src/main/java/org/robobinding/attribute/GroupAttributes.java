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

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import org.robobinding.AttributeResolutionException;
import org.robobinding.GroupedAttributeResolutionException;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupAttributes
{
	private Map<String, AbstractAttribute> resolvedChildAttributes = newHashMap();

	public GroupAttributes(PendingGroupAttributes pendingGroupAttributes, ChildAttributeResolverMappings resolverMappings)
	{
		resolveChildAttributes(pendingGroupAttributes, resolverMappings);
	}

	private void resolveChildAttributes(PendingGroupAttributes pendingGroupAttributes, ChildAttributeResolverMappings resolverMappings)
	{
		GroupedAttributeResolutionException groupResolutionErrors = new GroupedAttributeResolutionException();
		for (Map.Entry<String, String> attributeEntry : pendingGroupAttributes.presentAttributes())
		{
			String attribute = attributeEntry.getKey();
			ChildAttributeResolver resolver = resolverMappings.resolverFor(attribute);
			try
			{
				AbstractAttribute childAttribute = resolver.resolveChildAttribute(attribute, attributeEntry.getValue());
				resolvedChildAttributes.put(attribute, childAttribute);
			} catch (AttributeResolutionException e)
			{
				groupResolutionErrors.add(e);
			}
		}
		resolverMapper.validateResolvedChildAttributes(this);

		groupResolutionErrors.assertNoErrors();
	}

	public ValueModelAttribute valueModelAttributeFor(String attributeName)
	{
		return attributeFor(attributeName);
	}

	public StaticResourceAttribute staticResourceAttributeFor(String attributeName)
	{
		return attributeFor(attributeName);
	}

	public <T extends Enum<T>> EnumAttribute<T> enumAttributeFor(String attributeName)
	{
		return attributeFor(attributeName);
	}

	public boolean hasAttribute(String attributeName)
	{
		return resolvedChildAttributes.containsKey(attributeName);
	}

	@SuppressWarnings("unchecked")
	public <AttributeType extends AbstractAttribute> AttributeType attributeFor(String attributeName)
	{
		return (AttributeType) resolvedChildAttributes.get(attributeName);
	}
}
