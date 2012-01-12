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
package org.robobinding.viewattribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeInjector
{
	public <T extends View> void injectPropertyAttributeValues(PropertyViewAttribute<T> propertyViewAttribute, T view, String attributeValue, boolean preInitializeViews)
	{
		propertyViewAttribute.setView(view);
		propertyViewAttribute.setAttributeValue(attributeValue);
		propertyViewAttribute.setPreInitializeView(preInitializeViews);
	}

	public <T extends View> void injectCommandAttributeValues(AbstractCommandViewAttribute<T> commandViewAttribute, T view, String commandName)
	{
		commandViewAttribute.setView(view);
		commandViewAttribute.setCommandName(commandName);
	}

	public void injectGroupedAttributeValues(AbstractGroupedViewAttribute<View> groupedViewAttribute, View view, boolean preInitializeViews,
			GroupedAttributeDetails groupedAttributeDetails)
	{
		groupedViewAttribute.setViewAttributeInjector(this);
		groupedViewAttribute.setView(view);
		groupedViewAttribute.setPreInitializeViews(preInitializeViews);
		groupedViewAttribute.setGroupedAttributeDetails(groupedAttributeDetails);
	}

}
