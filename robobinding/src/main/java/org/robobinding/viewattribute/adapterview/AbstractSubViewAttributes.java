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
	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[]{layoutAttribute()};
	}

	@Override
	public void bindTo(BindingContext context)
	{
		View subView = createSubView(context);
		
		addSubView(subView, context.getAndroidContext());
		
		if(groupedAttributeDetails.hasAttribute(visibilityAttribute()))
		{
			SubViewVisibilityAttribute visibilityAttribute = createVisibilityAttribute(subView);
			visibilityAttribute.setView(subView);
			visibilityAttribute.setAttributeValue(groupedAttributeDetails.attributeValueFor(visibilityAttribute()));
			visibilityAttribute.bindTo(context);
		}
	}

	View createSubView(BindingContext context)
	{
		SubViewCreator subViewCreator = createSubViewCreator(context, groupedAttributeDetails.attributeValueFor(layoutAttribute()));
		
		if(groupedAttributeDetails.hasAttribute(subViewPresentationModelAttribute()))
		{
			String subViewPresentationModelAttributeValue = groupedAttributeDetails.attributeValueFor(subViewPresentationModelAttribute());
			return subViewCreator.createAndBindTo(subViewPresentationModelAttributeValue);
		}else
		{
			return subViewCreator.create();
		}
	}

	SubViewCreator createSubViewCreator(BindingContext context, String layoutAttributeValue)
	{
		return new SubViewCreator(context, layoutAttributeValue);
	}
	
	protected abstract String layoutAttribute();
	protected abstract String subViewPresentationModelAttribute();
	protected abstract String visibilityAttribute();
	protected abstract void addSubView(View subView, Context context);
	protected abstract SubViewVisibilityAttribute createVisibilityAttribute(View subView);
}