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
package org.robobinding.viewattribute.impl;

import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.GroupedAttributeDetails;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.AbstractViewAttributeInstantiator;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewListenersProvider;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewAttributeInstantiator
{
	ViewListenersProvider viewListenersProvider;
	ViewAttributeInstantiatorImplementor viewAttributeInstantiatorImplementor;

	public ViewAttributeInstantiator()
	{
		this.viewListenersProvider = new ViewListenersProviderImpl();
		viewAttributeInstantiatorImplementor = new ViewAttributeInstantiatorImplementor(viewListenersProvider);
	}

	public <PropertyViewAttributeType extends PropertyViewAttribute<? extends View>> PropertyViewAttributeType newPropertyViewAttribute(
			View view, Class<PropertyViewAttributeType> propertyViewAttributeClass, ValueModelAttribute attributeValue)
	{
		viewAttributeInstantiatorImplementor.setCurrentView(view);
		return viewAttributeInstantiatorImplementor.newPropertyViewAttribute(propertyViewAttributeClass, attributeValue);
	}

	public <CommandViewAttributeType extends AbstractCommandViewAttribute<? extends View>> CommandViewAttributeType newCommandViewAttribute(
			View view, Class<CommandViewAttributeType> commandViewAttributeClass, CommandAttribute attributeValue)
	{
		viewAttributeInstantiatorImplementor.setCurrentView(view);
		return viewAttributeInstantiatorImplementor.newCommandViewAttribute(commandViewAttributeClass, attributeValue);
	}

	@SuppressWarnings("unchecked")
	public <GroupedViewAttributeType extends AbstractGroupedViewAttribute<? extends View>> GroupedViewAttributeType newGroupedViewAttribute(
			View view, Class<GroupedViewAttributeType> groupedViewAttributeClass, GroupedAttributeDetails groupedAttributeDetails)
	{
		GroupedViewAttributeType groupedViewAttribute = (GroupedViewAttributeType)viewAttributeInstantiatorImplementor.newViewAttribute(groupedViewAttributeClass);
		((AbstractGroupedViewAttribute<View>) groupedViewAttribute).setView(view);
		groupedViewAttribute.setGroupedAttributeDetails(groupedAttributeDetails);
		groupedViewAttribute.setViewListenersProvider(viewListenersProvider);
		return groupedViewAttribute;
	}

	public static class ViewAttributeInstantiatorImplementor extends AbstractViewAttributeInstantiator
	{
		private View currentView;

		public ViewAttributeInstantiatorImplementor(ViewListenersProvider viewListenersProvider)
		{
			super(viewListenersProvider);
		}

		@Override
		protected View getView()
		{
			return currentView;
		}

		public void setCurrentView(View currentView)
		{
			this.currentView = currentView;
		}
		
		@Override
		public ViewAttribute newViewAttribute(Class<? extends ViewAttribute> viewAttributeClass)
		{
			return super.newViewAttribute(viewAttributeClass);
		}
	}
}