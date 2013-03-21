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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupAttributesBuilder
{
	private List<AbstractAttribute> childAttributeResolutions; 

	public static GroupAttributesBuilder aGroupAttributes()
	{
		return new GroupAttributesBuilder();
	}
	
	private GroupAttributesBuilder()
	{
		childAttributeResolutions = Lists.newArrayList();
	}
	
	public GroupAttributesBuilder withChildAttributeResolution(AbstractAttribute childAttribute)
	{
		childAttributeResolutions.add(childAttribute);
		return this;
	}
	
	public GroupAttributes build()
	{
		return new GroupAttributes(createPendingGroupAttributes(), createResolverMappings());
	}

	private ChildAttributeResolverMappings createResolverMappings()
	{
		ChildAttributeResolverMappings childAttributeResolverMappings = mock(ChildAttributeResolverMappings.class);
		ChildAttributeResolver childAttributeResolver = mock(ChildAttributeResolver.class);
		
		for (AbstractAttribute attribute : childAttributeResolutions) {
			when(childAttributeResolver.resolveChildAttribute(eq(attribute.getName()), anyString())).thenReturn(attribute);
			when(childAttributeResolverMappings.resolverFor(attribute.getName())).thenReturn(childAttributeResolver);
		}	
		
		return childAttributeResolverMappings;
	}

	private PendingGroupAttributes createPendingGroupAttributes()
	{
		Map<String, String> presentAttributes = Maps.newHashMap();
		for(AbstractAttribute childAttribute : childAttributeResolutions)
		{
			presentAttributes.put(childAttribute.getName(), null);
		}
		
		return new PendingGroupAttributes(presentAttributes);
	}
}