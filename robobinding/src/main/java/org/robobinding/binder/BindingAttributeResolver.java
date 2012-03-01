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
package org.robobinding.binder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.MalformedBindingAttributeException;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.GroupedAttributeDetailsImpl;

import android.view.View;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class BindingAttributeResolver
{
	private List<ViewAttribute> resolvedViewAttributes;
	private Map<String, String> pendingAttributeMappings;
	private Map<String, String> malformedBindingAttributes;

	public BindingAttributeResolver(Map<String, String> pendingAttributes)
	{
		this.pendingAttributeMappings = pendingAttributes;
		this.resolvedViewAttributes = Lists.newArrayList();
		this.malformedBindingAttributes = Maps.newHashMap();
	}

	void resolve(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		resolvePropertyViewAttributes(viewAttributeMappings);
		resolveCommandViewAttributes(viewAttributeMappings);
		resolveGroupedViewAttributes(viewAttributeMappings);
	}

	private void resolvePropertyViewAttributes(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (String propertyAttribute : viewAttributeMappings.getPropertyAttributes())
		{
			if (hasAttribute(propertyAttribute))
			{
				try
				{
					PropertyViewAttribute<View> propertyViewAttribute = viewAttributeMappings.createPropertyViewAttribute(propertyAttribute,
							getAttributeValue(propertyAttribute));
					resolveViewAttribute(propertyAttribute, propertyViewAttribute);
				} catch (MalformedBindingAttributeException e)
				{
					malformedBindingAttributes.put(propertyAttribute, e.getMessage());
					pendingAttributeMappings.remove(propertyAttribute);
				}
			}
		}
	}

	private void resolveCommandViewAttributes(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (String commandAttribute : viewAttributeMappings.getCommandAttributes())
		{
			if (hasAttribute(commandAttribute))
			{
				AbstractCommandViewAttribute<View> commandViewAttribute = viewAttributeMappings.createCommandViewAttribute(commandAttribute,
						getAttributeValue(commandAttribute));
				resolveViewAttribute(commandAttribute, commandViewAttribute);
			}
		}
	}

	private void resolveGroupedViewAttributes(BindingAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (GroupedAttributeDetailsImpl groupedAttributeDetails : viewAttributeMappings.getGroupedPropertyAttributes())
		{
			if (hasOneOfAttributes(groupedAttributeDetails.getSupportedAttributes()))
			{
				for (String attribute : groupedAttributeDetails.getSupportedAttributes())
				{
					if (hasAttribute(attribute))
					{
						groupedAttributeDetails.addPresentAttribute(attribute, getAttributeValue(attribute));
					}
				}

				AbstractGroupedViewAttribute<View> groupedViewAttribute = viewAttributeMappings.createGroupedViewAttribute(groupedAttributeDetails);
				resolveViewAttribute(groupedAttributeDetails.getPresentAttributes(), groupedViewAttribute);
			}
		}
	}

	private boolean hasAttribute(String attribute)
	{
		return pendingAttributeMappings.containsKey(attribute);
	}

	private String getAttributeValue(String attribute)
	{
		return pendingAttributeMappings.get(attribute);
	}

	private void resolveViewAttribute(String attribute, ViewAttribute viewAttribute)
	{
		pendingAttributeMappings.remove(attribute);
		resolvedViewAttributes.add(viewAttribute);
	}

	private void resolveViewAttribute(Collection<String> attributes, ViewAttribute viewAttribute)
	{
		for (String attribute : attributes)
			pendingAttributeMappings.remove(attribute);

		resolvedViewAttributes.add(viewAttribute);
	}

	private boolean hasOneOfAttributes(String[] attributes)
	{
		for (String attribute : attributes)
		{
			if (pendingAttributeMappings.containsKey(attribute))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isDone()
	{
		return pendingAttributeMappings.isEmpty();
	}

	public boolean hasUnresolvedAttributes()
	{
		return !pendingAttributeMappings.isEmpty();
	}

	public List<ViewAttribute> getResolvedViewAttributes()
	{
		return Collections.unmodifiableList(resolvedViewAttributes);
	}

	public void assertAllAttributesResolvedFor(View view)
	{
		if (!pendingAttributeMappings.isEmpty() || !malformedBindingAttributes.isEmpty())
			throw new BindingAttributeException(pendingAttributeMappings, malformedBindingAttributes, view);
	}
}
