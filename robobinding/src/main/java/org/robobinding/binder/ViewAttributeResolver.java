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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedPropertyViewAttribute;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewAttributeResolver
{
	private View view;
	private boolean preInitializeView;
	private List<ViewAttribute> resolvedViewAttributes;
	private Map<String, String> pendingAttributeMappings;

	public ViewAttributeResolver(View view, boolean preInitializeView, Map<String, String> pendingAttributes)
	{
		this.view = view;
		this.preInitializeView = preInitializeView;
		this.pendingAttributeMappings = pendingAttributes;
		this.resolvedViewAttributes = Lists.newArrayList();
	}

	void resolve(WidgetViewAttributeProviderAdapter<View> viewAttributeProvider)
	{
		ViewAttributeMappingsImpl<View> viewAttributeMappings = viewAttributeProvider.createViewAttributeMappings();
		
		resolvePropertyViewAttributes(viewAttributeMappings);
		resolveCommandViewAttributes(viewAttributeMappings);
		resolveGroupedPropertyViewAttributes(viewAttributeMappings);
	}

	private void resolvePropertyViewAttributes(ViewAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (String propertyAttribute : viewAttributeMappings.getPropertyAttributes())
		{
			if (hasAttribute(propertyAttribute))
			{
				PropertyViewAttribute<View> propertyViewAttribute = viewAttributeMappings.createPropertyViewAttribute(propertyAttribute);
				resolveAndInitializePropertyViewAttribute(propertyAttribute, propertyViewAttribute);
			}
		}
	}

	private boolean hasAttribute(String attribute)
	{
		return pendingAttributeMappings.containsKey(attribute);
	}

	private void resolveAndInitializePropertyViewAttribute(String propertyAttribute, PropertyViewAttribute<View> propertyViewAttribute)
	{
		resolveViewAttribute(propertyAttribute, propertyViewAttribute);
		
		propertyViewAttribute.setView(view);
		propertyViewAttribute.setPreInitializeView(preInitializeView);
		propertyViewAttribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(getAttributeValue(propertyAttribute)));
	}

	private String getAttributeValue(String attribute)
	{
		return pendingAttributeMappings.get(attribute);
	}

	private void resolveViewAttribute(String attribute, ViewAttribute viewAttribute)
	{
		resolveViewAttribute(Lists.newArrayList(attribute), viewAttribute);
	}

	private void resolveViewAttribute(List<String> attributes, ViewAttribute viewAttribute)
	{
		removeResolvedAttributes(attributes);
		resolvedViewAttributes.add(viewAttribute);
	}

	private void removeResolvedAttributes(List<String> attributes)
	{
		for (String attribute : attributes)
		{
			pendingAttributeMappings.remove(attribute);
		}
	}

	private void resolveCommandViewAttributes(ViewAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (String commandAttribute : viewAttributeMappings.getCommandAttributes())
		{
			if (hasAttribute(commandAttribute))
			{
				AbstractCommandViewAttribute<View> commandViewAttribute = viewAttributeMappings.createCommandViewAttribute(commandAttribute);
				resolveAndInitializeCommandViewAttribute(commandAttribute, commandViewAttribute);
			}
		}
	}

	private void resolveAndInitializeCommandViewAttribute(String attribute, AbstractCommandViewAttribute<View> commandViewAttribute)
	{
		resolveViewAttribute(attribute, commandViewAttribute);
	
		commandViewAttribute.setView(view);
		commandViewAttribute.setCommandName(getAttributeValue(attribute));
	}

	private void resolveGroupedPropertyViewAttributes(ViewAttributeMappingsImpl<View> viewAttributeMappings)
	{
		for (String[] groupAttributeNames : viewAttributeMappings.getGroupedPropertyAttributes())
		{
			if (hasOneOfAttributes(groupAttributeNames))
			{
				AbstractGroupedPropertyViewAttribute<View> groupedViewAttribute = viewAttributeMappings.createGroupedPropertyViewAttribute(groupAttributeNames);
				Map<String, String> attributes = Maps.newHashMap();
				
				for (String name : groupAttributeNames)
				{
					if (hasAttribute(name))
					{
						attributes.put(name, getAttributeValue(name));
					}
				}
				
				groupedViewAttribute.setChildAttributes(attributes);
			}
		}
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

	public String describeUnresolvedAttributes()
	{
		String unhandledAttributes = "";

		for (String attributeKey : pendingAttributeMappings.keySet())
			unhandledAttributes += attributeKey + ": " + pendingAttributeMappings.get(attributeKey) + "; ";

		return unhandledAttributes;
	}

	public List<ViewAttribute> getResolvedViewAttributes()
	{
		return Collections.unmodifiableList(resolvedViewAttributes);
	}
}
