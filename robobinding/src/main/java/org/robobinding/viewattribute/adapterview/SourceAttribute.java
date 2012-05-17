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
import org.robobinding.attributevalue.PropertyAttributeParser;
import org.robobinding.attributevalue.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.viewattribute.ChildAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SourceAttribute implements ChildAttribute
{
	private final DataSetAdapter<?> dataSetAdapter;
	private ValueModelAttribute attributeValue;

	public SourceAttribute(final DataSetAdapter<?> dataSetAdapter)
	{
		this.dataSetAdapter = dataSetAdapter;
	}
	
	@Override
	public void setAttributeValue(String name, String value)
	{
		this.attributeValue = new PropertyAttributeParser().parseAsValueModelAttribute(name, value);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void bindTo(BindingContext bindingContext)
	{
		PresentationModelAdapter presentationModelAdapter = bindingContext.getPresentationModelAdapter();
		DataSetValueModel dataSetValueModel = presentationModelAdapter.getDataSetPropertyValueModel(attributeValue.getPropertyName());
		dataSetAdapter.setValueModel(dataSetValueModel);
	}
}