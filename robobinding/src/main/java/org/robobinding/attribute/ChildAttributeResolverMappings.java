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

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildAttributeResolverMappings
{
	private Map<String, ChildAttributeResolver> childAttributeResolvers;
	
	public ChildAttributeResolverMappings()
	{
		childAttributeResolvers = Maps.newHashMap();
	}
	
	public void map(String attribute, ChildAttributeResolver resolver)
	{
		Validate.notEmpty(attribute, "Attribute cannot be empty");
		Validate.notNull(resolver, "Resolver cannot be null");
		
		childAttributeResolvers.put(attribute, resolver);
	}

	ChildAttributeResolver resolverFor(String attribute)
	{
		if(childAttributeResolvers.containsKey(attribute))
		{
			return childAttributeResolvers.get(attribute);
		}else
		{
			throw new RuntimeException("A ChildAttributeResolver for '"+attribute+"' is not specified");
		}
	}
}
