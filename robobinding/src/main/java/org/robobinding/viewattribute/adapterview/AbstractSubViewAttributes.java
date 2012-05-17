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

import org.robobinding.BindingContext;
import org.robobinding.attributevalue.StaticResourceAttribute;
import org.robobinding.attributevalue.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;

import android.content.Context;
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
	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[]{layoutAttribute()};
	}

	@Override
	protected void preBind(BindingContext bindingContext)
	{
		subView = createSubView(bindingContext);
		
		addSubView(subView, bindingContext.getContext());
	}
	
	View createSubView(BindingContext bindingContext)
	{
		SubViewCreator subViewCreator = createSubViewCreator(bindingContext, groupedAttributeDetails.staticResourceAttributeValueFor(layoutAttribute()));
		
		if(groupedAttributeDetails.hasAttribute(subViewPresentationModelAttribute()))
		{
			ValueModelAttribute presentationModelAttributeValue = groupedAttributeDetails.valueModelAttributeValueFor(subViewPresentationModelAttribute());
			return subViewCreator.createAndBindTo(presentationModelAttributeValue);
		}else
		{
			return subViewCreator.create();
		}
	}

	SubViewCreator createSubViewCreator(BindingContext bindingContext, StaticResourceAttribute layoutAttributeValue)
	{
		return new SubViewCreator(bindingContext, layoutAttributeValue);
	}

	@Override
	protected void setupChildAttributesBinding(ChildAttributesBinding binding)
	{
		if(groupedAttributeDetails.hasAttribute(visibilityAttribute()))
		{
			binding.add(new SubViewVisibilityAttribute(createVisibility(subView)), visibilityAttribute());
		}
	}

	protected abstract String layoutAttribute();
	protected abstract String subViewPresentationModelAttribute();
	protected abstract String visibilityAttribute();
	protected abstract void addSubView(View subView, Context context);
	protected abstract AbstractSubViewVisibility createVisibility(View subView);
}