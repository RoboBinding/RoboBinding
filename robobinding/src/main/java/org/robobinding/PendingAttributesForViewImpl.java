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
package org.robobinding;


import java.util.Collection;
import java.util.Map;



import android.view.View;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PendingAttributesForViewImpl implements PendingAttributesForView
{
	private View view;
	Map<String, String> attributeMappings;
	private ViewResolutionException resolutionErrors;
	public PendingAttributesForViewImpl(View view, Map<String, String> attributeMappings)
	{
		this.view = view;
		this.attributeMappings = Maps.newHashMap(attributeMappings);
		resolutionErrors = new ViewResolutionException(view);
	}
	
	@Override
	public View getView()
	{
		return view;
	}

	@Override
	public boolean isEmpty()
	{
		return attributeMappings.isEmpty();
	}

	@Override
	public ViewResolutionErrors resolveCompleted()
	{
		resolutionErrors.addUnrecognizedAttributes(attributeMappings.keySet());
		return resolutionErrors;
	}

	@Override
	public void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver)
	{
		if(attributeMappings.containsKey(attribute))
		{
			String attributeValue = attributeMappings.get(attribute);
			try
			{
				attributeResolver.resolve(view, attribute, attributeValue);
			}catch(AttributeResolutionException e)
			{
				resolutionErrors.addAttributeError(e);
			}
			
			attributeMappings.remove(attribute);
		}
	}

	@Override
	public void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver)
	{
		if (hasOneOfAttributes(attributeGroup))
		{
			Map<String, String> presentAttributeMappings = findPresentAttributeMappings(attributeGroup);
			Collection<String> presentAttributes = presentAttributeMappings.keySet();
			
			try
			{
				attributeGroupResolver.resolve(view, attributeGroup, presentAttributeMappings);
			}catch(AttributeGroupResolutionException e)
			{
				resolutionErrors.addAttributeGroupError(attributeGroup, e);
			}catch(AttributeResolutionException e)
			{
				resolutionErrors.addAttributeError(e);
			}
			
			removeAttributes(presentAttributes);
		}
	}

	private boolean hasOneOfAttributes(String[] attributes)
	{
		for (String attribute : attributes)
		{
			if (attributeMappings.containsKey(attribute))
			{
				return true;
			}
		}
		return false;
	}

	private Map<String, String> findPresentAttributeMappings(String[] attributes)
	{
		Map<String, String> presentAttributeMappings = Maps.newHashMap();
		for(String attribute : attributes)
		{
			if(attributeMappings.containsKey(attribute))
			{
				String attributeValue = attributeMappings.get(attribute);
				presentAttributeMappings.put(attribute, attributeValue);
			}
		}
		return presentAttributeMappings;
	}

	private void removeAttributes(Collection<String> attributes)
	{
		for(String attribute : attributes)
		{
			attributeMappings.remove(attribute);
		}
	}
}
