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


import static org.robobinding.attribute.ChildAttributeResolvers.predefinedMappingsAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.propertyAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttributes;

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
	protected DataSetAdapter<?> dataSetAdapter;
	
	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[]{SOURCE, ITEM_LAYOUT};
	}
	
	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
	{
		resolverMappings.map(valueModelAttributeResolver(), SOURCE);
		resolverMappings.map(propertyAttributeResolver(), ITEM_LAYOUT);
		resolverMappings.map(predefinedMappingsAttributeResolver(), ITEM_MAPPING);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void setupChildViewAttributes(ChildViewAttributes<T> childViewAttributes, BindingContext bindingContext)
	{
		dataSetAdapter = new DataSetAdapter(bindingContext);
		
		childViewAttributes.add(SOURCE, new SourceAttribute(dataSetAdapter));
		childViewAttributes.add(ITEM_LAYOUT, new ItemLayoutAttribute(view, dataSetAdapter));
		
		if(childViewAttributes.hasAttribute(ITEM_MAPPING))
			childViewAttributes.add(ITEM_MAPPING,new ItemMappingAttribute(dataSetAdapter));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void postBind(BindingContext bindingContext)
	{
		dataSetAdapter.observeChangesOnTheValueModel();
		((AdapterView)view).setAdapter(dataSetAdapter);
	}
}
