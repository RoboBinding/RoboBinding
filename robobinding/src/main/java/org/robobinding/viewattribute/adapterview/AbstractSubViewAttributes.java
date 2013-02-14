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
package org.robobinding.viewattribute.adapterview;

import static org.robobinding.attribute.ChildAttributeResolvers.staticResourceAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;

import android.R;
import android.content.Context;
import android.view.InflateException;
import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractSubViewAttributes<T extends AdapterView<?>> extends AbstractGroupedViewAttribute<T>
{
	private View subView;
	private ChildAttributeBindings childAttributeBindings;
	private BindingContext bindingContext;

	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[] { layoutAttribute() };
	}

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
	{
		resolverMappings.map(staticResourceAttributeResolver(), layoutAttribute());
		resolverMappings.map(valueModelAttributeResolver(), subViewPresentationModelAttribute());
		resolverMappings.map(valueModelAttributeResolver(), visibilityAttribute());
	}

	@Override
	protected void preBind(BindingContext bindingContext)
	{
		this.bindingContext = bindingContext;
	}
	
	@Override
	protected void setupChildAttributeBindings(ChildAttributeBindings binding)
	{
		this.childAttributeBindings = binding;
		
		try {
			subView = createSubView(bindingContext);
		} catch (InflateException e) {
			return;
		}
		
		addSubView(subView, bindingContext.getContext());
		if (groupedAttribute.hasAttribute(visibilityAttribute()))
		{
			binding.add(new SubViewVisibilityAttribute(createVisibility(subView)), visibilityAttribute());
		}
	}

	View createSubView(BindingContext bindingContext)
	{
		int layoutId = 0;
		try {
			layoutId = groupedAttribute.staticResourceAttributeFor(layoutAttribute()).getResourceId(bindingContext.getContext());
		} catch (RuntimeException e) {
			childAttributeBindings.addChildAttributeError(layoutAttribute(), e);
		}
		
		SubViewCreator subViewCreator = new SubViewCreator(bindingContext, layoutId);

		//instantiate a dummy view?
		//or just don't register the visibility attribute?
		//don't register the visibility attribute will be easier to understand
		if (groupedAttribute.hasAttribute(subViewPresentationModelAttribute()))
		{
			try
			{
				ValueModelAttribute presentationModelAttributeValue = groupedAttribute.valueModelAttributeFor(subViewPresentationModelAttribute());
				return subViewCreator.createAndBindTo(presentationModelAttributeValue);
			} catch (RuntimeException e)
			{
				childAttributeBindings.addChildAttributeError(subViewPresentationModelAttribute(), e);
				return subViewCreator.create();
			}
		} else
		{
			return subViewCreator.create();
		}
	}

	protected abstract String layoutAttribute();

	protected abstract String subViewPresentationModelAttribute();

	protected abstract String visibilityAttribute();

	protected abstract void addSubView(View subView, Context context);

	protected abstract AbstractSubViewVisibility createVisibility(View subView);
}