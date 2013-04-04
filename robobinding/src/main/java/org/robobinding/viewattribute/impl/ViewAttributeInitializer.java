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
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.AbstractViewAttributeInitializer;
import org.robobinding.viewattribute.GroupedViewAttributeConfig;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewListenersInjector;

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
	ViewListenersInjector viewListenersInjector;
	ViewAttributeInitializerDelegate delegate;

	public ViewAttributeInitializer()
	{
		this.viewListenersInjector = new ViewListenersProvider();
		delegate = new ViewAttributeInitializerDelegate(viewListenersInjector);
	}

	public <ViewType extends View, PropertyViewAttributeType extends PropertyViewAttribute<ViewType>> PropertyViewAttributeType initializePropertyViewAttribute(
			ViewType view, PropertyViewAttributeType propertyViewAttribute, ValueModelAttribute attribute)
	{
		delegate.setCurrentView(view);
		delegate.<ViewType, PropertyViewAttributeType>initializePropertyViewAttribute(propertyViewAttribute, attribute);
		return propertyViewAttribute;
	}

	public <ViewType extends View, CommandViewAttributeType extends AbstractCommandViewAttribute<ViewType>> CommandViewAttributeType initializeCommandViewAttribute(
			ViewType view, CommandViewAttributeType commandViewAttribute, CommandAttribute attribute)
	{
		delegate.setCurrentView(view);
		delegate.<ViewType, CommandViewAttributeType>initializeCommandViewAttribute(commandViewAttribute, attribute);
		return commandViewAttribute;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <ViewType extends View, GroupedViewAttributeType extends AbstractGroupedViewAttribute<? extends View>> GroupedViewAttributeType initializeGroupedViewAttribute(
			ViewType view, GroupedViewAttributeType groupedViewAttribute, PendingGroupAttributes pendingGroupAttributes)
	{
		groupedViewAttribute.initialize(new GroupedViewAttributeConfig(view, pendingGroupAttributes, viewListenersInjector));
		return groupedViewAttribute;
	}

	private static class ViewAttributeInitializerDelegate extends AbstractViewAttributeInitializer
	{
		private View currentView;

		public ViewAttributeInitializerDelegate(ViewListenersInjector viewListenersInjector)
		{
			super(viewListenersInjector);
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