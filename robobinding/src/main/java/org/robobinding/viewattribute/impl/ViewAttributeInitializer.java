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
import org.robobinding.attribute.GroupedAttributeDescriptor;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.AbstractViewAttributeInitializer;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewListenersProvider;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewAttributeInitializer
{
	ViewListenersProvider viewListenersProvider;
	ViewAttributeInitializerImplementor viewAttributeInitializerImplementor;

	public ViewAttributeInitializer()
	{
		this.viewListenersProvider = new ViewListenersProviderImpl();
		viewAttributeInitializerImplementor = new ViewAttributeInitializerImplementor(viewListenersProvider);
	}

	public <PropertyViewAttributeType extends PropertyViewAttribute<? extends View>> PropertyViewAttributeType initializePropertyViewAttribute(
			View view, PropertyViewAttributeType propertyViewAttribute, ValueModelAttribute attributeValue)
	{
		viewAttributeInitializerImplementor.setCurrentView(view);
		return viewAttributeInitializerImplementor.newPropertyViewAttribute(propertyViewAttribute, attributeValue);
	}

	public <CommandViewAttributeType extends AbstractCommandViewAttribute<? extends View>> CommandViewAttributeType initializeCommandViewAttribute(
			View view, CommandViewAttributeType commandViewAttribute, CommandAttribute attributeValue)
	{
		viewAttributeInitializerImplementor.setCurrentView(view);
		return viewAttributeInitializerImplementor.newCommandViewAttribute(commandViewAttribute, attributeValue);
	}

	@SuppressWarnings("unchecked")
	public <GroupedViewAttributeType extends AbstractGroupedViewAttribute<? extends View>> GroupedViewAttributeType initializeGroupedViewAttribute(
			View view, GroupedViewAttributeType groupedViewAttribute, GroupedAttributeDescriptor groupedAttributeDescriptor)
	{
		((AbstractGroupedViewAttribute<View>) groupedViewAttribute).setView(view);
		groupedViewAttribute.setGroupedAttributeDescriptor(groupedAttributeDescriptor);
		groupedViewAttribute.setViewListenersProvider(viewListenersProvider);
		return groupedViewAttribute;
	}

	public static class ViewAttributeInitializerImplementor extends AbstractViewAttributeInitializer
	{
		private View currentView;

		public ViewAttributeInitializerImplementor(ViewListenersProvider viewListenersProvider)
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
	}
}