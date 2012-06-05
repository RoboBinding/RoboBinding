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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupedAttributeBuilder
{
	private List<AbstractAttribute> childAttributeResolutions; 

	public static GroupedAttributeBuilder aGroupedAttribute()
	{
		return new GroupedAttributeBuilder();
	}
	
	private GroupedAttributeBuilder()
	{
		childAttributeResolutions = Lists.newArrayList();
	}
	
	public GroupedAttributeBuilder withChildAttributeResolution(AbstractAttribute childAttribute)
	{
		childAttributeResolutions.add(childAttribute);
		return this;
	}
	
	public GroupedAttribute build()
	{
		return new GroupedAttribute(createDescriptor(), createResolverMapper());
	}

	private ChildAttributeResolverMapper createResolverMapper()
	{
		ChildAttributeResolverMapper resolverMapper = mock(ChildAttributeResolverMapper.class);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable
			{
				ChildAttributeResolverMappings resolverMappings = (ChildAttributeResolverMappings)invocation.getArguments()[0];
				for(AbstractAttribute childAttribute : childAttributeResolutions)
				{
					String attributeName = childAttribute.getName();
					ChildAttributeResolver resolver = mock(ChildAttributeResolver.class);
					when(resolver.resolveChildAttribute(eq(attributeName), anyString())).thenReturn(childAttribute);
					resolverMappings.map(resolver, attributeName);
				}
				return null;
			}
		}).when(resolverMapper).mapChildAttributeResolvers(any(ChildAttributeResolverMappings.class));
		return resolverMapper;
	}

	private GroupedAttributeDescriptor createDescriptor()
	{
		Map<String, String> presentAttributes = Maps.newHashMap();
		for(AbstractAttribute childAttribute : childAttributeResolutions)
		{
			presentAttributes.put(childAttribute.getName(), null);
		}
		
		return new GroupedAttributeDescriptor(presentAttributes);
	}
}