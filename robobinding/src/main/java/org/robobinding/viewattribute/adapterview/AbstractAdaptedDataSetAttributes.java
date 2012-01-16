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
package org.robobinding.viewattribute.adapterview;


import java.util.Collections;
import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.presentationmodel.DataSetAdapter;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;

import android.content.Context;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractAdaptedDataSetAttributes<T extends AdapterView<?>> extends AbstractGroupedViewAttribute<T>
{
	public static final String SOURCE = "source";
	public static final String ITEM_LAYOUT = "itemLayout";
	public static final String ITEM_MAPPING = "itemMapping";
	
	protected List<AdapterViewAttribute> childViewAttributes;
	
	@Override
	protected void initializeChildViewAttributes()
	{
		validateAttributes();
		
		childViewAttributes = Lists.newArrayList();
		childViewAttributes.add(new SourceAttribute(groupedAttributeDetails.attributeValueFor(SOURCE)));
		childViewAttributes.add(new ItemLayoutAttribute(view, groupedAttributeDetails.attributeValueFor(ITEM_LAYOUT)));
		
		if (groupedAttributeDetails.hasAttribute(ITEM_MAPPING))
			childViewAttributes.add(new ItemMappingAttribute(groupedAttributeDetails.attributeValueFor(ITEM_MAPPING)));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		DataSetAdapter dataSetAdapter = new DataSetAdapter(context, preInitializeViews);
		
		for (AdapterViewAttribute adapterViewAttribute : childViewAttributes)
			adapterViewAttribute.bind(dataSetAdapter, presentationModelAdapter, context);
		
		dataSetAdapter.observeChangesOnTheValueModel();
		((AdapterView)view).setAdapter(dataSetAdapter);
	}

	protected void validateAttributes()
	{
		assertAttributesArePresent(SOURCE, ITEM_LAYOUT);
	}
	
	List<AdapterViewAttribute> getAdapterViewAttributes()
	{
		return Collections.unmodifiableList(childViewAttributes);
	}
}
