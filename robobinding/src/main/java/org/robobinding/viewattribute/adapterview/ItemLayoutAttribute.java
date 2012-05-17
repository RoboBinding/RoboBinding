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
import org.robobinding.attributevalue.AbstractPropertyAttributeValue;
import org.robobinding.attributevalue.PropertyAttributeValueParser;
import org.robobinding.attributevalue.StaticResourceAttributeValue;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.ChildAttribute;
import org.robobinding.viewattribute.ViewAttribute;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ItemLayoutAttribute implements ChildAttribute
{
	private final AdapterView<?> adapterView;
	protected final DataSetAdapter<?> dataSetAdapter;
	private ViewAttribute layoutAttribute;
	
	public ItemLayoutAttribute(AdapterView<?> adapterView, DataSetAdapter<?> dataSetAdapter)
	{
		this.adapterView = adapterView;
		this.dataSetAdapter = dataSetAdapter;
	}

	@Override
	public void setAttributeValue(String value)
	{
		AbstractPropertyAttributeValue attributeValue = PropertyAttributeValueParser.parse(value);
		if (attributeValue.isStaticResource())
			layoutAttribute = new StaticLayoutAttribute(attributeValue.asStaticResourceAttributeValue());
		else
		{
			DynamicLayoutAttribute dynamicLayoutAttribute = new DynamicLayoutAttribute();
			dynamicLayoutAttribute.setView(adapterView);
			dynamicLayoutAttribute.setAttributeValue(attributeValue.asValueModelAttributeValue());
			layoutAttribute = dynamicLayoutAttribute;
		}
	}
	
	@Override
	public void bindTo(BindingContext bindingContext)
	{
		layoutAttribute.bindTo(bindingContext);		
	}
	
	protected void updateLayoutId(int layoutId)
	{
		dataSetAdapter.setItemLayoutId(layoutId);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	class DynamicLayoutAttribute extends AbstractReadOnlyPropertyViewAttribute<AdapterView<?>, Integer>
	{
		@Override
		protected void valueModelUpdated(Integer newItemLayoutId)
		{
			updateLayoutId(newItemLayoutId);
			((AdapterView)view).setAdapter(dataSetAdapter);
		}
	}
	
	class StaticLayoutAttribute implements ViewAttribute
	{
		private StaticResourceAttributeValue attributeValue;

		public StaticLayoutAttribute(StaticResourceAttributeValue attributeValue)
		{
			this.attributeValue = attributeValue;
		}

		@Override
		public void bindTo(BindingContext bindingContext)
		{
			int itemLayoutId = attributeValue.getResourceId(bindingContext.getContext());
			updateLayoutId(itemLayoutId);
		}
	}

}