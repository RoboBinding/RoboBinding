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

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMapper;
import org.robobinding.attribute.GroupedAttribute;
import org.robobinding.attribute.GroupedAttributeDescriptor;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements ViewAttribute, ChildAttributeResolverMapper
{
	private static final String[] NO_COMPULSORY_ATTRIBUTES = new String[0];
	
	protected T view;
	private ChildViewAttributes<T> childViewAttributes;
	private boolean childViewAttributesNotSetup;
	
	public void initialize(GroupedViewAttributeConfig<T> config)
	{
		view = config.getView();
		childViewAttributes = createChildViewAttributes(config.getDescriptor(), config.getViewListenersProvider());
		childViewAttributesNotSetup = true;
	}
	
	private ChildViewAttributes<T> createChildViewAttributes(GroupedAttributeDescriptor descriptor, ViewListenersProvider viewListenersProvider)
	{
		descriptor.assertAttributesArePresent(getCompulsoryAttributes());
		GroupedAttribute groupedAttribute = new GroupedAttribute(descriptor, this);

		ViewAttributeInstantiator viewAttributeInstantiator = new ViewAttributeInstantiator(viewListenersProvider);

		return new ChildViewAttributes<T>(groupedAttribute, viewAttributeInstantiator);		
	}

	@Override
	public void validateResolvedChildAttributes(GroupedAttribute groupedAttribute) 
	{
	}

	@Override
	public final void preinitializeView(BindingContext bindingContext)
	{
		setupChildViewAttributesOnceOnly(childViewAttributes, bindingContext);
		childViewAttributes.preinitializeView(bindingContext);
	}
	
	private void setupChildViewAttributesOnceOnly(ChildViewAttributes<T> childViewAttributes, BindingContext bindingContext)
	{
		if(childViewAttributesNotSetup)
		{
			setupChildViewAttributes(childViewAttributes, bindingContext);
			childViewAttributesNotSetup = false;
		}
	}
	
	protected abstract void setupChildViewAttributes(ChildViewAttributes<T> childViewAttributes, BindingContext bindingContext);

	@Override
	public final void bindTo(BindingContext bindingContext)
	{
		setupChildViewAttributesOnceOnly(childViewAttributes, bindingContext);
		
		childViewAttributes.bindTo(bindingContext);
		
		postBind(bindingContext);
	}
	
	protected void postBind(BindingContext bindingContext)
	{
		
	}

	protected String[] getCompulsoryAttributes()
	{
		return NO_COMPULSORY_ATTRIBUTES;
	}

	private class ViewAttributeInstantiator extends AbstractViewAttributeInstantiator
	{
		public ViewAttributeInstantiator(ViewListenersProvider viewListenersProvider)
		{
			super(viewListenersProvider);
		}
		@Override
		protected T getView()
		{
			return view;
		}
	}
}
