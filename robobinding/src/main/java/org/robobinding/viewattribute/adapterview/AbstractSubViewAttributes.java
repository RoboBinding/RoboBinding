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

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.listview.SubViewVisibilityAttribute;

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
	protected void initializeChildViewAttributes()
	{
		validateAttributes();
	}
	
	private void validateAttributes()
	{
		assertAttributesArePresent(layoutAttribute());
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		View subView = createSubView(presentationModelAdapter, context);
		
		addSubView(subView);
		
		if(groupedAttributeDetails.hasAttribute(visibilityAttribute()))
		{
			SubViewVisibilityAttribute visibilityAttribute = createVisibilityAttribute(subView);
			visibilityAttribute.setPreInitializeView(preInitializeViews);
			visibilityAttribute.setAttributeValue(groupedAttributeDetails.attributeValueFor(visibilityAttribute()));
			visibilityAttribute.bind(presentationModelAdapter, context);
		}
	}

	View createSubView(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		SubViewCreator subViewCreator = createSubViewCreator(context, groupedAttributeDetails.attributeValueFor(layoutAttribute()));
		
		if(groupedAttributeDetails.hasAttribute(subViewPresentationModelAttribute()))
		{
			String subViewPresentationModelAttributeValue = groupedAttributeDetails.attributeValueFor(subViewPresentationModelAttribute());
			return subViewCreator.createAndBindTo(subViewPresentationModelAttributeValue, presentationModelAdapter);
		}else
		{
			return subViewCreator.create();
		}
	}

	SubViewCreator createSubViewCreator(Context context, String layoutAttributeValue)
	{
		return new SubViewCreator(context, layoutAttributeValue);
	}
	
	protected abstract String layoutAttribute();
	protected abstract String subViewPresentationModelAttribute();
	protected abstract String visibilityAttribute();
	protected abstract void addSubView(View subView);
	protected abstract SubViewVisibilityAttribute createVisibilityAttribute(View subView);
}