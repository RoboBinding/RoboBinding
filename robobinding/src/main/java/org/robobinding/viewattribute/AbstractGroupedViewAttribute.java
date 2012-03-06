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
package org.robobinding.viewattribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements ViewAttribute
{
	private static final String[] NO_COMPULSORY_ATTRIBUTES = new String[0];
	
	protected T view;
	protected GroupedAttributeDetails groupedAttributeDetails;
	private AbstractViewAttributeInstantiator viewAttributeInstantiator;
	private ViewListenersProvider viewListenersProvider;
	
	public void setView(T view)
	{
		this.view = view;
	}

	public void setGroupedAttributeDetails(GroupedAttributeDetails groupedAttributeDetails)
	{
		groupedAttributeDetails.assertAttributesArePresent(view, getCompulsoryAttributes());
		this.groupedAttributeDetails = groupedAttributeDetails;
	}
	
	public void setViewListenersProvider(ViewListenersProvider viewListenersProvider)
	{
		this.viewListenersProvider = viewListenersProvider;
		getViewAttributeInstantiator().setViewListenersIfRequired(this, view);
	}
	
	protected String[] getCompulsoryAttributes()
	{
		return NO_COMPULSORY_ATTRIBUTES;
	}
	
	public void postInitialization()
	{
	}
	
	protected AbstractViewAttributeInstantiator getViewAttributeInstantiator()
	{
		if (viewAttributeInstantiator == null)
			viewAttributeInstantiator = new ViewAttributeInstantiator();
		return viewAttributeInstantiator;
	}
	
	private class ViewAttributeInstantiator extends AbstractViewAttributeInstantiator
	{
		public ViewAttributeInstantiator()
		{
			super(AbstractGroupedViewAttribute.this.viewListenersProvider);
		}
		@Override
		protected String attributeValueFor(String attribute)
		{
			return groupedAttributeDetails.attributeValueFor(attribute);
		}
		@Override
		protected T getView()
		{
			return view;
		}
	}
}
