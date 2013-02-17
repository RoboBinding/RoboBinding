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

import android.view.InflateException;
import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewAttributes<T extends AdapterView<?>> extends AbstractGroupedViewAttribute<T>
{
	private View subView;
	private SubViewAttributesStrategy<T> subViewAttributesStrategy;
	private SubViewCreator subViewCreator;
	
	public SubViewAttributes(SubViewAttributesStrategy<T> subViewAttributesStrategy, SubViewCreator subViewCreator)
	{
		this.subViewAttributesStrategy = subViewAttributesStrategy;
		this.subViewCreator = subViewCreator;
	}

	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[] { layoutAttribute() };
	}

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
	{
		resolverMappings.map(staticResourceAttributeResolver(), layoutAttribute());
		resolverMappings.map(valueModelAttributeResolver(), subViewPresentationModel());
		resolverMappings.map(valueModelAttributeResolver(), visibilityAttribute());
	}

	@Override
	protected void preBind(BindingContext bindingContext)
	{
	}
	
	@Override
	protected void setupChildAttributeBindings(ChildAttributeBindings childAttributeBindings, BindingContext bindingContext)
	{
		try {
			subView = createSubView(childAttributeBindings, bindingContext);
		} catch (InflateException e) {
			return;
		}
		
		subViewAttributesStrategy.addSubView(view, subView, bindingContext.getContext());
		if (groupedAttribute.hasAttribute(visibilityAttribute()))
		{
			childAttributeBindings.add(new SubViewVisibilityAttribute(subViewAttributesStrategy.createVisibility(view, subView)), visibilityAttribute());
		}
	}

	View createSubView(ChildAttributeBindings childAttributeBindings, BindingContext bindingContext)
	{
		int layoutId = 0;
		try {
			layoutId = groupedAttribute.staticResourceAttributeFor(layoutAttribute()).getResourceId(bindingContext.getContext());
		} catch (RuntimeException e) {
			childAttributeBindings.addChildAttributeError(layoutAttribute(), e);
		}
		
		if (groupedAttribute.hasAttribute(subViewPresentationModel()))
		{
			try
			{
				ValueModelAttribute presentationModelAttributeValue = groupedAttribute.valueModelAttributeFor(subViewPresentationModel());
				return subViewCreator.createAndBindTo(presentationModelAttributeValue);
			} catch (RuntimeException e)
			{
				childAttributeBindings.addChildAttributeError(subViewPresentationModel(), e);
				return subViewCreator.create();
			}
		} else
		{
			return subViewCreator.create();
		}
	}

	private String visibilityAttribute()
	{
		return subViewAttributesStrategy.visibilityAttribute();
	}

	private String subViewPresentationModel()
	{
		return subViewAttributesStrategy.subViewPresentationModelAttribute();
	}

	private String layoutAttribute()
	{
		return subViewAttributesStrategy.layoutAttribute();
	}
}