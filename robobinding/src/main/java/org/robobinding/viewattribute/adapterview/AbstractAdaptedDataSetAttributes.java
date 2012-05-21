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


import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import static org.robobinding.attribute.ChildAttributeResolvers.*;

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
		resolverMappings.map(SOURCE, valueModelAttributeResolver());
		resolverMappings.map(ITEM_LAYOUT, propertyAttributeResolver());
		resolverMappings.map(ITEM_MAPPING, plainAttributeResolver());
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	protected void preBind(BindingContext bindingContext)
	{
		dataSetAdapter = new DataSetAdapter(bindingContext);
	}

	@Override
	protected void setupChildAttributeBindings(ChildAttributeBindings binding)
	{
		binding.add(new SourceAttribute(dataSetAdapter), SOURCE);
		binding.add(new ItemLayoutAttribute(view, dataSetAdapter), ITEM_LAYOUT);
		if(groupedAttribute.hasAttribute(ITEM_MAPPING))
		{
			binding.add(new ItemMappingAttribute(dataSetAdapter),ITEM_MAPPING);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void postBind(BindingContext bindingContext)
	{
		dataSetAdapter.observeChangesOnTheValueModel();
		((AdapterView)view).setAdapter(dataSetAdapter);
	}
}
