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
import org.robobinding.viewattribute.PropertyViewAttribute;

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
			PropertyViewAttribute<View> visibilityAttribute = createVisibilityAttribute(subView);
			visibilityAttribute.bind(presentationModelAdapter, context);
		}
	}

	View createSubView(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		SubViewCreator subViewCreator;
		
		String layoutAttributeValue = groupedAttributeDetails.attributeValueFor(layoutAttribute());
		if(groupedAttributeDetails.hasAttribute(subViewPresentationModelAttribute()))
		{
			String subViewPresentationModelAttributeValue = groupedAttributeDetails.attributeValueFor(subViewPresentationModelAttribute());
			subViewCreator = new SubViewCreator(layoutAttributeValue, subViewPresentationModelAttributeValue);
		}else
		{
			subViewCreator = new SubViewCreator(layoutAttributeValue);
		}
		
		return subViewCreator.bindAndCreate(presentationModelAdapter, context);
	}
	
	protected abstract String layoutAttribute();
	protected abstract String subViewPresentationModelAttribute();
	protected abstract String visibilityAttribute();
	protected abstract void addSubView(View subView);
	protected abstract PropertyViewAttribute<View> createVisibilityAttribute(View subView);
}